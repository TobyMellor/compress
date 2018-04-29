package uk.co.tobymellor.compress;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DiscoverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        final Article[] articles = {
                new Article("Title test", "Author summary :)", "Company nice", "Author name"),
                new Article("Title test", "Author summary :)", "Company nice", "Author name"),
                new Article("Title test", "Author summary :)", "Company nice", "Author name"),
                new Article("Title test", "Author summary :)", "Company nice", "Author name")
        };

        ListView list = findViewById(R.id.list_cards);

        list.setAdapter(
                new ArticleAdapter(this, articles, list)
        );
    }
}
