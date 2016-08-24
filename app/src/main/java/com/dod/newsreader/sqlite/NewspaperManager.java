package com.dod.newsreader.sqlite;

import android.content.Context;
import android.util.Log;

import com.dod.newsreader.model.Newspaper;

import java.util.List;

/**
 * Created by Deniz on 17.08.2016.
 */
public class NewspaperManager {

    private final List<Newspaper> newspapers;
    private final SQLiteHelper sqLiteHelper;
    private String LOG_TAG = "NewspaperManager";

    public NewspaperManager(Context context)
    {
        this.sqLiteHelper = new SQLiteHelper(context);
        this.newspapers = sqLiteHelper.selectAll(Newspaper.class);
        Log.i(this.LOG_TAG , this.newspapers != null ? "Gazeteler veritabanında : "+this.newspapers : "Gazete Yok");

    }

    public List<Newspaper> getNewspapers() {
        return newspapers;
    }
}
