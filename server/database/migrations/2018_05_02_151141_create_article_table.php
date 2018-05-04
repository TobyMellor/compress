<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateArticleTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('article', function (Blueprint $table) {
            $table->increments('id');

            $table->string('title');
            $table->string('author_summary');
            $table->string('three_sentence_summary');
            $table->string('seven_sentence_summary');
            $table->string('article_link');

            $table->integer('author_id')
                  ->unsigned()
                  ->nullable();
            $table->foreign('author_id')
                  ->references('id')
                  ->on('author');

            $table->integer('news_outlet_genre_id')
                  ->unsigned();
            $table->foreign('news_outlet_genre_id')
                  ->references('id')
                  ->on('news_outlet_genre');

            $table->timestamp('date');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('article');
    }
}
