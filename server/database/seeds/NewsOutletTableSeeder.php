<?php

use Illuminate\Database\Seeder;

use App\NewsOutlet;

class NewsOutletTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $newsOutlets = [
            [
                'slug' => 'bbc-news',
                'name' => 'BBC News'
            ]/*,
            [
                'id'   => 'fortune',
                'name' => 'Fortune'
            ],
            [
                'id'   => 'mashable',
                'name' => 'Mashable'
            ],
            [
                'id'   => 'polygon',
                'name' => 'Polygon'
            ],
            [
                'id'   => 'the-verge',
                'name' => 'The Verge'
            ]*/
        ];

        NewsOutlet::insert($newsOutlets);
    }
}
