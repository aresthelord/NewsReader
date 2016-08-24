package com.dod.newsreader.newspaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dod.newsreader.R;
import com.dod.newsreader.model.Newspaper;

import java.util.List;

/**
 * Created by Deniz on 17.08.2016.
 */
public class NewspaperAdapter extends BaseAdapter {

    private Context context = null;
    private List<Newspaper> newspapers = null;
    private LayoutInflater layoutInflater = null;

    public NewspaperAdapter(Context context, List<Newspaper> objects) {
        this.context = context;
        this.newspapers = objects;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.newspapers != null ? this.newspapers.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return this.newspapers != null ? this.newspapers.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return this.newspapers != null ? Long.valueOf(this.newspapers.get(position).getId()).longValue() : -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Newspaper newspaper = (Newspaper) getItem(position);

        View rowView = layoutInflater.inflate(R.layout.list_item_newspaper, null);
        ImageView imgIcon = (ImageView) rowView.findViewById(R.id.imgIcon);
        imgIcon.setImageResource(newspaper.getResourceId());
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        tvName.setText(newspaper.getName());
        TextView tvDetails = (TextView) rowView.findViewById(R.id.tvDetails);
        tvDetails.setText(String.valueOf(newspaper.getId()));

        return rowView;
    }
}
