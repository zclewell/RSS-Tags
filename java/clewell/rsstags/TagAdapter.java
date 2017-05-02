package clewell.rsstags;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zach on 4/25/17.
 */

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    ArrayList<String> tags = new ArrayList<String>();

    public TagAdapter(ArrayList<String> tagSet) {
        tags = tagSet;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View tagItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item, parent, false);
        return new ViewHolder(tagItem);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final String tag = tags.get(position);
        System.out.println(tag);
        holder.name.setText(tag);
    }

    public int getItemCount() {
        return tags.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
