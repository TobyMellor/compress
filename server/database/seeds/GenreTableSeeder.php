<?php

use Illuminate\Database\Seeder;

use App\{
    NewsOutlet,
    Genre,
    NewsOutletGenre
};

class GenreTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $newsOutlets = [
            'bbc-news' => [
                ['slug' => 'uk',                     'name' => 'UK'],
                ['slug' => 'world',                  'name' => 'World'],
                ['slug' => 'business',               'name' => 'Business'],
                ['slug' => 'politics',               'name' => 'Politics'],
                ['slug' => 'tech',                   'name' => 'Tech'],
                ['slug' => 'science',                'name' => 'Science'],
                ['slug' => 'health',                 'name' => 'Health'],
                ['slug' => 'family-and-education',   'name' => 'Family & Education'],
                ['slug' => 'entertainment-and-arts', 'name' => 'Entertainment & Arts'],
                ['slug' => 'stories',                'name' => 'Stories']
            ],
            'mashable' => [
                ['slug' => 'video',         'name' => 'Video'],
                ['slug' => 'entertainment', 'name' => 'Entertainment'],
                ['slug' => 'culture',       'name' => 'Culture'],
                ['slug' => 'tech',          'name' => 'Tech'],
                ['slug' => 'science',       'name' => 'Science'],
                ['slug' => 'business',      'name' => 'Business'],
                ['slug' => 'social-good',   'name' => 'Social Good']
            ]
        ];

        foreach ($newsOutlets as $newsOutlet => $genres) {
            $newsOutlet = NewsOutlet::where('slug', $newsOutlet)->first();

            foreach ($genres as $genre) {
                $genre = Genre::updateOrCreate(['slug' => $genre['slug']], $genre);

                NewsOutletGenre::insert([
                    'news_outlet_id' => $newsOutlet->id,
                    'genre_id'       => $genre->id
                ]);
            }
        }
    }
}
