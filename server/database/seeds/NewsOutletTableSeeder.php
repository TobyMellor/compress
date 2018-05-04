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
                'slug'       => 'bbc-news',
                'name'       => 'BBC News'
            ],
            [
                'slug'       => 'mashable',
                'name'       => 'Mashable'
            ]/*,
            [
                'id'   => 'fortune',
                'name' => 'Fortune'
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
