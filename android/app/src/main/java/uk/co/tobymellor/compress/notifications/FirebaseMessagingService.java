package uk.co.tobymellor.compress.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;

import uk.co.tobymellor.compress.MainActivity;
import uk.co.tobymellor.compress.R;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    public final static String SUBSCRIBED_TOPIC = "compress";
    public final static String PREFERENCE_NOTIFICATIONS = "compress_notifications";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (!isNotificationsEnabled(getApplicationContext())) return;

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

    public static Boolean toggleNotification(Context context) {
        Boolean isNotificationsEnabled = !FirebaseMessagingService.isNotificationsEnabled(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences(FirebaseMessagingService.PREFERENCE_NOTIFICATIONS, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(FirebaseMessagingService.PREFERENCE_NOTIFICATIONS, isNotificationsEnabled);
        editor.apply();

        if (isNotificationsEnabled) {
            Toast.makeText(context, R.string.notifications_enabled, Toast.LENGTH_SHORT).show();
            enableNotifications();
        } else {
            Toast.makeText(context, R.string.notifications_disabled, Toast.LENGTH_SHORT).show();
            disableNotifications();
        }

        return isNotificationsEnabled;
    }

    private static void enableNotifications() {
        FirebaseMessaging.getInstance().subscribeToTopic(FirebaseMessagingService.SUBSCRIBED_TOPIC);
        FirebaseInstanceId.getInstance().getToken();
    }

    private static void disableNotifications() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(FirebaseMessagingService.SUBSCRIBED_TOPIC);
    }

    public static void init(MainActivity mainActivity) {
        FirebaseApp.initializeApp(mainActivity);

        if (FirebaseMessagingService.isNotificationsEnabled(mainActivity)) {
            enableNotifications();
        } else {
            disableNotifications();
        }
    }

    public static Boolean isNotificationsEnabled(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FirebaseMessagingService.PREFERENCE_NOTIFICATIONS, Context.MODE_PRIVATE);

       return sharedPreferences.getBoolean(FirebaseMessagingService.PREFERENCE_NOTIFICATIONS, true);
    }
}
