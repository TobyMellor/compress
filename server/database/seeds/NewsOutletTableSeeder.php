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
                'name'       => 'BBC News',
                'created_at' => new DateTime(),
                'updated_at' => new DateTime()
            ],
            [
                'slug'       => 'mashable',
                'name'       => 'Mashable',
                'created_at' => new DateTime(),
                'updated_at' => new DateTime()
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
