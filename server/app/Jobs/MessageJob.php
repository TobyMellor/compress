<?php

namespace App\Jobs;

use LaravelFCM\Message\PayloadDataBuilder;
use LaravelFCM\Message\PayloadNotificationBuilder;
use LaravelFCM\Message\Topics;
use FCM;

use App\FirebaseToken;
use App\Article;

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
        $randomArticle = Article::inRandomOrder()->first();

        $notificationBuilder = new PayloadNotificationBuilder('BREAKING NEWS: ' . $randomArticle->title);
        $notificationBuilder->setBody($randomArticle->author_summary)
                            ->setSound('default');

        $dataBuilder = new PayloadDataBuilder();
        $dataBuilder->addData(['article_link' => $randomArticle->article_link]);

        $notification = $notificationBuilder->build();
        $data = $dataBuilder->build();

        $topic = new Topics();
        $topic->topic('notifications');

        $downstreamResponse = FCM::sendToTopic($topic, null, $notification, $data);
    }
}
