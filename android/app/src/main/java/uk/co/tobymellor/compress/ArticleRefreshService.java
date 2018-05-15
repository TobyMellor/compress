package uk.co.tobymellor.compress;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

public class ArticleRefreshService extends IntentService {

    private final Handler handler = new Handler();

    private final static String SERVICE_NAME = "Article Refresh Service";
    private final static int DELAY = 60000; // 1 minute

    public static MainActivity mainActivity;

    public ArticleRefreshService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        handler.postDelayed(ArticleRefreshRunnable, DELAY);
    }

    final Runnable ArticleRefreshRunnable = new Runnable() {
        public void run() {
            Log.d(SERVICE_NAME, "Refreshed Articles");
            MainActivity.getArticleManager().pullNewArticles(mainActivity);

            handler.postDelayed(ArticleRefreshRunnable, DELAY);
        }
    };
}