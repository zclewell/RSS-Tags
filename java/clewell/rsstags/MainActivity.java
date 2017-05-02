package clewell.rsstags;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView list;
    private Feed myFeed;
    private ArrayList<Article> allArticles = new ArrayList<Article>();
    private ArticleAdapter myAdapter;
    private final String address = "http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize RecyclerView
        list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myAdapter = new ArticleAdapter(allArticles);
        list.setAdapter(myAdapter);

        //Get articles
        FeedAsyncTask task = new FeedAsyncTask(this);
        task.execute(address);
    }

    public class FeedAsyncTask extends AsyncTask<String, Void, Feed> {
        Context context;

        public FeedAsyncTask(Context context) {
            this.context = context;
        }

        protected Feed doInBackground(String... params) {
            try {
                //Parses given url and makes a feed object from it
                URL temp = new URL(params[0]);
                Feed output = Parser.parse(temp);
                return output;
            } catch (Exception ex) {
                return null;
            }
        }

        protected void onPostExecute(Feed feed) {
            if (feed == null) {
                Toast.makeText(context, "Error reading Feed", Toast.LENGTH_SHORT);
                return;
            }
            for (Article curr : feed.getArticles()) {
                //Takes articles out of Feed object and puts them into a article list
                //Done this way to allow support for multiple feeds
                allArticles.add(curr);
            }
            myAdapter.notifyDataSetChanged();
        }
    }
}
