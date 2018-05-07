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
            
            $table->string('genre_slug');
            $table->foreign('genre_slug')
                  ->references('slug')
                  ->on('genre');

            $table->string('news_outlet_slug');
            $table->foreign('news_outlet_slug')
                  ->references('slug')
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
