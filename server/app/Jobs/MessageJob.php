<?php

namespace App\Jobs;

use LaravelFCM\Message\OptionsBuilder;
use LaravelFCM\Message\PayloadDataBuilder;
use LaravelFCM\Message\PayloadNotificationBuilder;
use FCM;

use App\FirebaseToken;
use App\NewsArticle;

class MessageJob extends Job
{
    /**
     * Create a new job instance.
     *
     * @return void
     */
    public function __construct()
    {
        //
    }

    /**
     * Execute the job.
     *
     * @return void
     */
    public function handle()
    {
        $optionBuilder = new OptionsBuilder();
        $optionBuilder->setTimeToLive(60*20);

        $randomArticle = NewsArticle::inRandomOrder()->first();

        $notificationBuilder = new PayloadNotificationBuilder('BREAKING NEWS: ' . $randomArticle->title);
        $notificationBuilder->setBody($randomArticle->author_summary)
                            ->setSound('default');

        $dataBuilder = new PayloadDataBuilder();
        $dataBuilder->addData(['article_link' => $randomArticle->article_link]);

        $option = $optionBuilder->build();
        $notification = $notificationBuilder->build();
        $data = $dataBuilder->build();

        // You must change it to get your tokens
        $tokens = FirebaseToken::pluck('token')->toArray();

        $downstreamResponse = FCM::sendTo($tokens, $option, $notification, $data);

        //return Array - you must remove all this tokens in your database
        var_dump($downstreamResponse->tokensToDelete());

        //return Array (key : oldToken, value : new token - you must change the token in your database )
        var_dump($downstreamResponse->tokensToModify());

        //return Array - you should try to resend the message to the tokens in the array
        var_dump($downstreamResponse->tokensToRetry());

        // return Array (key:token, value:errror) - in production you should remove from your database the tokens present in this array
        var_dump($downstreamResponse->tokensWithError());
    }
}
