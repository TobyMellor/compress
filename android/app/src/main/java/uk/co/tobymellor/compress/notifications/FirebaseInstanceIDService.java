package uk.co.tobymellor.compress.notifications;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import uk.co.tobymellor.compress.JSONTask;
import uk.co.tobymellor.compress.MainActivity;
import uk.co.tobymellor.compress.models.Manager;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody
                .Builder()
                .add("token", token)
                .build();

        Request request = new Request
                .Builder()
                .url(Manager.BASE_URL + "/testing")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
