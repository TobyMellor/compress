package uk.co.tobymellor.compress.views;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.tobymellor.compress.MainActivity;
import uk.co.tobymellor.compress.R;
import uk.co.tobymellor.compress.UserGuideActivity;

public class SettingsFragment extends ComPressFragment {
    public SettingsFragment(LayoutInflater inflater, ViewGroup container, final MainActivity mainActivity) {
        fragment = inflater.inflate(R.layout.fragment_settings, container, false);

        fragment.findViewById(R.id.list_view_user_guide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.startActivity(new Intent(mainActivity, UserGuideActivity.class));
            }
        });
    }

    public View getView() {
        return fragment;
    }
}