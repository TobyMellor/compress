package uk.co.tobymellor.compress;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import uk.co.tobymellor.compress.models.articles.ArticleManager;
import uk.co.tobymellor.compress.models.genres.GenreManager;
import uk.co.tobymellor.compress.models.news_outlet_genres.NewsOutletGenreManager;
import uk.co.tobymellor.compress.models.news_outlets.NewsOutletManager;
import uk.co.tobymellor.compress.models.read_later.ReadLaterManager;
import uk.co.tobymellor.compress.views.DiscoverFragment;
import uk.co.tobymellor.compress.views.ReadLaterFragment;
import uk.co.tobymellor.compress.views.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    private static NewsOutletManager newsOutletManager = null;
    private static GenreManager genreManager = null;
    private static NewsOutletGenreManager newsOutletGenreManager = null;
    private static ArticleManager articleManager = null;
    private static ReadLaterManager readLaterManager = null;

    private static ReadLaterFragment readLaterFragment = null;
    private static DiscoverFragment discoverFragment   = null;

    public final static String BASE_URL = "http://46.101.28.103/";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();

        if (articleManager == null) {
            try {
                newsOutletManager      = new NewsOutletManager();
                genreManager           = new GenreManager();
                newsOutletGenreManager = new NewsOutletGenreManager();
                articleManager         = new ArticleManager();
                readLaterManager       = new ReadLaterManager(getSharedPreferences(ReadLaterManager.PREFERENCES_FILE, Context.MODE_PRIVATE));
            } catch (InterruptedException | ExecutionException | JSONException | ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        System.out.println(savedInstanceState);
        mViewPager.setCurrentItem(savedInstanceState != null ? savedInstanceState.getInt("current_item") : 1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("current_item", 2);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public static NewsOutletManager getNewsOutletManager() {
        return newsOutletManager;
    }

    public static GenreManager getGenreManager() {
        return genreManager;
    }

    public static NewsOutletGenreManager getNewsOutletGenreManager() {
        return newsOutletGenreManager;
    }

    public static ArticleManager getArticleManager() {
        return articleManager;
    }

    public static ReadLaterManager getReadLaterManager() {
        return readLaterManager;
    }

    public static ReadLaterFragment getReadLaterFragment() {
        return readLaterFragment;
    }

    public static DiscoverFragment getDiscoverFragment() {
        return discoverFragment;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
            //
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            switch(getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    readLaterFragment = new ReadLaterFragment(inflater, container);

                    return readLaterFragment.getView();
                case 2:
                    discoverFragment = new DiscoverFragment(inflater, container);

                    return discoverFragment.getView();
                case 3:
                    return new SettingsFragment(inflater, container, (MainActivity) inflater.getContext()).getView();
            }

            return null;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
