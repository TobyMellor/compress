<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateNewsOutletGenreTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('news_outlet_genre', function (Blueprint $table) {
            $table->increments('id');

            $table->integer('genre_id')
                  ->unsigned();
            $table->foreign('genre_id')
                  ->references('id')
                  ->on('genre');

            $table->integer('news_outlet_id')
                  ->unsigned();
            $table->foreign('news_outlet_id')
                  ->references('id')
                  ->on('news_outlet');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('news_outlet');
    }
}
