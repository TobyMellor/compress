package uk.co.tobymellor.compress.views;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import uk.co.tobymellor.compress.MainActivity;
import uk.co.tobymellor.compress.R;
import uk.co.tobymellor.compress.UserGuideActivity;
import uk.co.tobymellor.compress.notifications.FirebaseMessagingService;

public class SettingsFragment extends ComPressFragment {
    public SettingsFragment(LayoutInflater inflater, ViewGroup container, final MainActivity mainActivity) {
        fragment = inflater.inflate(R.layout.fragment_settings, container, false);

        fragment.findViewById(R.id.list_view_user_guide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.startActivity(new Intent(mainActivity, UserGuideActivity.class));
            }
        });

        toggleNotificationView(FirebaseMessagingService.isNotificationsEnabled(mainActivity));

        fragment.findViewById(R.id.list_view_toggle_notifications).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isNotificationsEnabled = FirebaseMessagingService.toggleNotification(mainActivity);

                toggleNotificationView(isNotificationsEnabled);
            }
        });
    }

    public View getView() {
        return fragment;
    }

    private void toggleNotificationView(Boolean isNotificationsEnabled) {
        ((ImageButton) fragment.findViewById(R.id.image_toggle_notifications)).setImageResource(
                isNotificationsEnabled
                        ? R.drawable.ic_close_black_24dp
                        : R.drawable.ic_check_black_24dp
        );

        ((TextView) fragment.findViewById(R.id.text_toggle_notifications)).setText(
                isNotificationsEnabled
                        ? R.string.disable_notifications
                        : R.string.enable_notifications
        );
    }
}