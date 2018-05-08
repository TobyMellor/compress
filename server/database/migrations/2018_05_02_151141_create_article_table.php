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
            $table->longtext('author_summary');

            $table->longtext('short_sentence_summary')
                  ->nullable();
            $table->longtext('long_sentence_summary')
                  ->nullable();

            $table->string('article_image_link')
                  ->nullable();
            $table->string('article_link')
                  ->unique();

            $table->integer('author_id')
                  ->unsigned()
                  ->nullable();
            $table->foreign('author_id')
                  ->references('id')
                  ->on('author');

            $table->integer('news_outlet_genre_id')
                  ->unsigned()
                  ->nullable();
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
