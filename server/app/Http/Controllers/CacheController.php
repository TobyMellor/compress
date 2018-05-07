<?php

namespace App\Http\Controllers;

use App\CheckCache;

use Carbon\Carbon;

date_default_timezone_set('GMT');

/**
 * 1. Get CheckCache's where their upperbound and lowerbound straddles
 *    the $to date
 *        1.1 From that, select the CheckCache that has the oldest lowerbound
 * 2. Get CheckCache's where their upperbound and lowerbound straddles
 *    the $from date
 *        2.1 From that, select the CheckCache that has the newest upperbound
 * 3. Set the $to date to the 1.1's lowerbound, set the $from date to
 *    the 2.1's upperbound. Repeat step 1.
 */
class CacheController {
    private function getCheckCachesBetweenBounds(Carbon $lowerBound, Carbon $upperBound, String $newsOutletSlug, Array $checkCaches = []) {
        
        // Try to find a CheckCache that happened before/after $lowerBound,
        // return the straddler that expands closer to $upperBound
        $lowerBoundStraddler = CheckCache::where('lower_bound', '<', $lowerBound)
                                          ->where('upper_bound', '>', $lowerBound)
                                          ->where('news_outlet_slug', $newsOutletSlug)
                                          ->orderBy('upper_bound', 'desc')
                                          ->first();

        // If there's a previous check that happened before/after $lowerBound
        if ($lowerBoundStraddler) {
            $checkCaches[] = $lowerBoundStraddler;

            // If that CheckCache covers the whole range we're checking, we've finished
            if ($lowerBoundStraddler->upper_bound->gt($upperBound)) {
                return $checkCaches;
            }
        }

        // Try to find a CheckCache that happened before/after $upperBound,
        // return the straddler that expands closer to $lowerBound
        $upperBoundStraddler = CheckCache::where('lower_bound', '<', $upperBound)
                                          ->where('upper_bound', '>', $upperBound)
                                          ->where('news_outlet_slug', $newsOutletSlug)
                                          ->orderBy('lower_bound', 'ASC')
                                          ->first();

        // If there's a previous check that happened before/after $upperBound
        if ($upperBoundStraddler) {
            $checkCaches[] = $upperBoundStraddler;

            // If that CheckCache covers the whole range we're checking, we've finished
            if ($upperBoundStraddler->lower_bound->lt($lowerBound)) {
                return $checkCaches;
            }
        }

        // If we have two CheckCache's straddling both $upperBound and $lowerBound
        if ($lowerBoundStraddler && $upperBoundStraddler) {
            
            // If the $lowerBoundStraddler and $upperBoundStraddler don't overlap (aka there's still room for more checkCaches to be found)
            if ($lowerBoundStraddler->upper_bound->lt($upperBoundStraddler->lower_bound)) {
                return $this->getCheckCachesBetweenBounds($lowerBoundStraddler->upper_bound, $upperBoundStraddler->lower_bound, $newsOutletSlug, $checkCaches); // iterate with narrower bounds
            }
        } else if ($lowerBoundStraddler) {
            return $this->getCheckCachesBetweenBounds($lowerBoundStraddler->upper_bound, $upperBound, $newsOutletSlug, $checkCaches);
        } else if ($upperBoundStraddler) {
            return $this->getCheckCachesBetweenBounds($lowerBound, $upperBoundStraddler->lower_bound, $newsOutletSlug, $checkCaches);
        } else {
            $middleCheckCaches = CheckCache::where('lower_bound', '>', $lowerBound)
                                           ->where('upper_bound', '<', $upperBound)
                                           ->where('news_outlet_slug', $newsOutletSlug)
                                           ->orderBy('lower_bound', 'ASC');

            $lowestMiddleCheckCache  = $middleCheckCaches->first();
            $highestMiddleCheckCache = $middleCheckCaches->count() > 1 ? $middleCheckCaches->get()->last() : null;

            if ($lowestMiddleCheckCache && $highestMiddleCheckCache) {

                // Get the lowest middle and the highest middle, add/sub 1 second to make them straddle the boundary
                return $this->getCheckCachesBetweenBounds($lowestMiddleCheckCache->lower_bound->addSecond(1), $highestMiddleCheckCache->upper_bound->subSecond(1), $newsOutletSlug, $checkCaches);
            } else if ($lowestMiddleCheckCache) {
                return $this->getCheckCachesBetweenBounds($lowestMiddleCheckCache->lower_bound->addSecond(1), $lowestMiddleCheckCache->upper_bound->subSecond(1), $newsOutletSlug, $checkCaches);
            }
        }

        // There's no straddlers, or check caches between the boundaries
        return $checkCaches;
    }

    public function isFullyCached(Array $newsOutletSlugs, Carbon $from, Carbon $to) {
        $newsOutletsUncachedRanges = $this->getNewsOutletsUncachedRanges($newsOutletSlugs, $from, $to);

        foreach ($newsOutletsUncachedRanges as $newsOutletUncachedRange) {
            if (sizeOf($newsOutletUncachedRange) > 0) {
                return false;
            }
        }

        return true;
    }

    private function getNewsOutletsUncachedRanges(Array $newsOutletSlugs, Carbon $from, Carbon $to) {
        $newsOutletsUncachedRanges = [];

        foreach ($newsOutletSlugs as $newsOutletSlug) {
            $checkCaches = $this->getCheckCachesBetweenBounds($from, $to, $newsOutletSlug);

            $newsOutletsUncachedRanges[$newsOutletSlug] = $this->getNewsOutletUncachedRanges($checkCaches, $from, $to);
        }

        return $newsOutletsUncachedRanges;
    }

    public function getNewsOutletCacheRanges(String $newsOutletSlug, ?Carbon $from, Carbon $to) {
        if ($from) {
            if ($from->eq($to)) return [];

            $checkCaches = $this->getCheckCachesBetweenBounds($from, $to, $newsOutletSlug);
        } else {
            $checkCaches = CheckCache::where('news_outlet_slug', $newsOutletSlug)
                                     ->where('upper_bound', '<=', $to)
                                     ->orderBy('lower_bound', 'ASC')
                                     ->get()
                                     ->all();
        }

        $uncachedRanges = array_map(function($uncachedRange) {
            $uncachedRange['cached'] = false;

            return $uncachedRange;
        }, $this->getNewsOutletUncachedRanges($checkCaches, $from, $to));

        $cachedRanges = array_map(function($checkCache) {
            return [
                'from'   => $checkCache->lower_bound,
                'to'     => $checkCache->upper_bound,
                'cached' => true
            ];
        }, $this->mergeRanges($checkCaches));

        $cacheRanges = array_merge($uncachedRanges, $cachedRanges);

        usort($cacheRanges, function($a, $b) {
            return $a['to'] < $b['to'];
        });

        return $cacheRanges;
    }

    // Step 1: Merge overlapping checkCaches
    // Step 2: Find the gaps
    private function getNewsOutletUncachedRanges(Array $checkCaches, ?Carbon $from, Carbon $to) {
        if (sizeOf($checkCaches) === 0) {
            if ($from) {
                return [['from' => $from, 'to' => $to]];
            }

            return [['to' => $to]];
        }

        // Merge the potential overlapping CheckCache's to make things easier
        $checkCaches = $this->mergeRanges($checkCaches);

        $uncachedRanges = [];
        $firstCheckCache = $checkCaches[0];

        // If the first CheckCache straddles $from
        if ($from && $firstCheckCache->lower_bound->lte($from) && $firstCheckCache->upper_bound->gte($from)) {

            // If the first CheckCache straddles both $from and $to, there is no uncached ranges
            if ($firstCheckCache->upper_bound->gte($to)) {
                return [];
            }
        } else {
            if (!$from) {
                $uncachedRanges[] = ['from' => $from, 'to' => $firstCheckCache->lower_bound];
            } else {
                $uncachedRanges[] = ['to' => $firstCheckCache->lower_bound];
            }
        }

        if (sizeOf($checkCaches) > 1) {
            $uncachedRanges[] = ['from' => $firstCheckCache->upper_bound];
        } else if (sizeOf($checkCaches) === 1) {
            $uncachedRanges[] = ['from' => $firstCheckCache->upper_bound, 'to' => $to];
        }

        unset($checkCaches[0]);

        foreach ($checkCaches as $index => $checkCache) {
            if ($index < sizeOf($checkCaches) - 1) {
                $nextCheckCache = $checkCaches[$index + 1];

                $uncachedRanges[sizeOf($uncachedRanges) - 1]['to'] = $nextCheckCache->lower_bound;
                $uncachedRanges[] = ['from' => $nextCheckCache->upper_bound];
            } else {
                if ($checkCache->upper_bound->gte($to)) {
                    unset($uncachedRanges[sizeOf($uncachedRanges) - 1]);
                } else {
                    $uncachedRanges[sizeOf($uncachedRanges) - 1]['to'] = $to;
                }
            }
        }

        return $uncachedRanges;
    }

    public function mergeRanges(Array $checkCaches) {
        foreach ($checkCaches as $index => $checkCache) {
            if ($index >= sizeOf($checkCaches) - 1) continue;

            $nextCheckCache = $checkCaches[$index + 1];

            if ($checkCache->upper_bound->gt($nextCheckCache->lower_bound)) {
                $nextCheckCache->lower_bound = $checkCache->lower_bound->format('Y-m-d H:i:s');
                
                $checkCaches[$index] = null;
            }
        }

        return array_values(array_filter($checkCaches));
    }
}