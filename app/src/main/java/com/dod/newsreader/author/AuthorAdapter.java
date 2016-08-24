package com.dod.newsreader.author;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dod.newsreader.R;
import com.dod.newsreader.model.Author;

import java.util.List;

/**
 * Created by Deniz on 17.08.2016.
 */
public class AuthorAdapter extends BaseAdapter {

    private Context context = null;
    private List<Author> authors = null;
    private LayoutInflater layoutInflater = null;

    public AuthorAdapter(Context context, List<Author> objects) {
        this.context = context;
        this.authors = objects;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.authors != null ? this.authors.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return this.authors != null ? this.authors.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return this.authors != null ? Long.valueOf(this.authors.get(position).getId()).longValue() : -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Author author = (Author) getItem(position);

        View rowView = layoutInflater.inflate(R.layout.list_item_newspaper, null);
        ImageView imgIcon = (ImageView) rowView.findViewById(R.id.imgIcon);
        imgIcon.setImageResource(author.getResourceId());
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        tvName.setText(author.getName());
        TextView tvDetails = (TextView) rowView.findViewById(R.id.tvDetails);
        tvDetails.setText(String.valueOf(author.getId()));

        return rowView;
    }
}
