package clewell.rsstags;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zach on 4/20/17.
 */

public class Feed {
    private String title;
    private String copyright;
    private String language;
    private ArrayList<Article> articles = new ArrayList<Article>();

    public Feed(String title, String copyright, String language) {
        this.title = title;
        this.copyright = copyright;
        this.language = language;
    }

    public void addArticle(Article article) {
        articles.add(article);
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }
}
