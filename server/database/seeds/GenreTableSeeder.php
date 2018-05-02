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
            'bbc-news' => ['uk', 'world', 'business', 'politics', 'tech', 'science', 'health', 'family-and-education', 'entertainment-and-arts', 'stories']
        ];

        foreach ($newsOutlets as $newsOutlet => $genres) {
            $newsOutlet = NewsOutlet::where('slug', $newsOutlet)->first();

            foreach ($genres as $genre) {
                $genre = Genre::updateOrCreate(['slug' => $genre], ['slug' => $genre]);

                NewsOutletGenre::insert([
                    'news_outlet_id' => $newsOutlet->id;
                    'genre_id'       => $genre->id
                ]);
            }
        }
    }
}
