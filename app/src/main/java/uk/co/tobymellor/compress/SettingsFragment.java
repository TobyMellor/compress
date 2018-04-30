package uk.co.tobymellor.compress;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SettingsFragment {
    View fragment;

    public SettingsFragment(LayoutInflater inflater, ViewGroup container) {
        fragment = inflater.inflate(R.layout.fragment_settings, container, false);
    }

    public View getView() {
        return fragment;
    }
}
