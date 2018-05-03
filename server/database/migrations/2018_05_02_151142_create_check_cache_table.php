<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateCheckCacheTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('check_cache', function (Blueprint $table) {
            $table->increments('id');

            $table->integer('news_outlet_id')
                  ->unsigned();
            $table->foreign('news_outlet_id')
                  ->references('id')
                  ->on('news_outlet');

            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('check_cache');
    }
}
