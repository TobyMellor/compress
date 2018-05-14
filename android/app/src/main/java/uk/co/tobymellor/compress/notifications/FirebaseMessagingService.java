package uk.co.tobymellor.compress.notifications;

import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    public final static String SUBSCRIBED_TOPIC = "compress";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Toast.makeText(this, remoteMessage.getData().get("message"), Toast.LENGTH_SHORT).show();
    }
}
