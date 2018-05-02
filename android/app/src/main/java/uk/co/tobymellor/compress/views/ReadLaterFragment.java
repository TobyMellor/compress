package uk.co.tobymellor.compress.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.tobymellor.compress.R;
import uk.co.tobymellor.compress.models.articles.Article;
import uk.co.tobymellor.compress.models.authors.Author;
import uk.co.tobymellor.compress.models.genres.Genre;
import uk.co.tobymellor.compress.models.news_outlets.NewsOutlet;
import uk.co.tobymellor.compress.views.card.ArticleAdapter;

public class ReadLaterFragment {
    View fragment;

    public ReadLaterFragment(LayoutInflater inflater, ViewGroup container) {
        fragment = inflater.inflate(R.layout.fragment_read_later, container, false);
    }

    public View getView() {
        Date date = new Date();

        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse("2018-03-06T11:22:32.000Z");
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        final Article[] articles = {
                new Article("Thousands without water for a third day after UK's big freeze",
                        "Thousands of homes are without water for a third day in parts of the UK after a sudden rise in temperature caused frozen pipes to burst.",
                        "Water companies say a thaw has led to burst water mains and leaks.\n" +
                                "\n" +
                                "In Wales, 1,500 properties remain without water, Welsh Water said.\n" +
                                "\n" +
                                "Thames Water, which supplies water to London, says around 5,000 customers are still cut off.\n",
                        "Companies have been supplying bottled water while they reconnect customers in London, Kent, Sussex and Wales.\n" +
                                "\n" +
                                "Water companies say a thaw has led to burst water mains and leaks.\n" +
                                "\n" +
                                "In Wales, 1,500 properties remain without water, Welsh Water said.\n" +
                                "\n" +
                                "The company warned water could be discoloured when supplies are reconnected.\n" +
                                "\n" +
                                "Thames Water, which supplies water to London, says around 5,000 customers are still cut off.\n" +
                                "\n" +
                                "In Hastings, East Sussex and surrounding areas some 3,000 homes have low water pressure or no supply.\n" +
                                "\n" +
                                "On day three of no water, Sandra Daniels, who lives in Hastings, tweeted: " +
                                "Toilets are disgusting again until we steal water in containers from family tomorrow. Want a shower and hair wash." +
                                "Environment minister Therese Coffey said Ofwat would be \"Given any powers it needs\" when it reviews how companies have acted.",
                        "http://www.bbc.co.uk/news/uk-43298773",
                        date,
                        "2 weeks ago",
                        new Genre("technology", "Technology"),
                        new Author("John Smith", new NewsOutlet("BBC News")))
        };

        ListView list = fragment.findViewById(R.id.list_cards);

        list.setAdapter(
                new ArticleAdapter(fragment.getContext(), articles, list)
        );

        return fragment;
    }
}
