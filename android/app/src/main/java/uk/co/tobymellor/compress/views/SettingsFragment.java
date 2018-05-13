package uk.co.tobymellor.compress.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import uk.co.tobymellor.compress.R;

public class SettingsFragment extends ComPressFragment {
    public SettingsFragment(LayoutInflater inflater, ViewGroup container) {
        fragment = inflater.inflate(R.layout.fragment_settings, container, false);
    }

    public View getView() {
        return fragment;
    }
}
