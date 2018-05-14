package uk.co.tobymellor.compress.notifications;

import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Toast.makeText(this, remoteMessage.getData().get("message"), Toast.LENGTH_SHORT).show();
    }
}
