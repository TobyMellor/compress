package uk.co.tobymellor.compress.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;

import uk.co.tobymellor.compress.R;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    public final static String SUBSCRIBED_TOPIC = "compress";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        createNotificationChannel();

        Intent externalArticle = new Intent(Intent.ACTION_VIEW);
        externalArticle.setData(Uri.parse(remoteMessage.getData().get("article_link")));

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, externalArticle, 0);

        NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setSmallIcon(R.drawable.ic_compress_blue_24dp)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        notificationManager.notify(1, notificationCompatBuilder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getString(R.string.channel_id);
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, name, importance);

            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
