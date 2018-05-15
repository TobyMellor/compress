package uk.co.tobymellor.compress;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import uk.co.tobymellor.compress.views.ComPressFragment;
import uk.co.tobymellor.compress.views.DiscoverFragment;
import uk.co.tobymellor.compress.views.ReadLaterFragment;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        MainActivity mainActivity = (MainActivity) context;
        TextView noConnection     = mainActivity.findViewById(R.id.text_no_internet_connection);

        if (isConnectedOrConnecting(context)) {
            shouldDisplaySplashScreen();

            noConnection.setVisibility(View.GONE);
        } else {
            noConnection.setVisibility(View.VISIBLE);
        }
    }

    public Boolean shouldDisplaySplashScreen() {
        return shouldDisplaySplashScreen(MainActivity.getReadLaterFragment()) || shouldDisplaySplashScreen(MainActivity.getDiscoverFragment());
    }

    public Boolean shouldDisplaySplashScreen(final ComPressFragment compressFragment) {
        if (compressFragment == null) return false;

        MainActivity mainActivity                 = (MainActivity) compressFragment.getFragment().getContext();
        ConstraintLayout constraintErrorContainer = compressFragment.getFragment().findViewById(R.id.constraint_error_container);
        TextView textErrorMessage                 = constraintErrorContainer.findViewById(R.id.text_error_message);

        if (compressFragment instanceof ReadLaterFragment && compressFragment.getArticleAdapter().articles.size() == 0) {
            textErrorMessage.setText(R.string.no_articles);
        } else if (!isConnectedOrConnecting(mainActivity)) {
            textErrorMessage.setText(R.string.no_internet_connection);
        } else {
            constraintErrorContainer.setVisibility(View.GONE);

            return false;
        }

        constraintErrorContainer.setVisibility(View.VISIBLE);

        return true;
    }

    private Boolean isConnectedOrConnecting(final Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
