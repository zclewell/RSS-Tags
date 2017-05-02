package clewell.rsstags;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zach on 4/24/17.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    ArrayList<Article> articles;
    public static final String ARTICLE = "ARTICLE";

    public ArticleAdapter(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View articleItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(articleItem);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final Article article = articles.get(position);

        holder.title.setText(article.getTitle());
        if (article.getDescription().length() < 60) {
            holder.description.setText(article.getDescription());
        } else {
            holder.description.setText(article.getDescription().substring(0, 60) + "...");
        }
        holder.date.setText(article.getPubDate());
        holder.view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Launches Detail activity when view is clicked
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra(ARTICLE, article);
                v.getContext().startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return articles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView title;
        public TextView description;
        public TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.description);
            date = (TextView) itemView.findViewById(R.id.pubDate);
        }
    }
}
