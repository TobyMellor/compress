package uk.co.tobymellor.compress;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

public class DiscoverCardView extends ComPressCardView {
    public DiscoverCardView(Context context, ViewGroup container, @NonNull Article article) {
        super(context, container, article, R.layout.card);
    }
}
