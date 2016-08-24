package com.dod.newsreader;

import android.app.Application;
import android.util.Log;

import com.dod.newsreader.sqlite.AuthorManager;
import com.dod.newsreader.sqlite.NewspaperManager;

/**
 * Created by Deniz on 17.08.2016.
 */
public class NewsReaderApplication extends Application {

    private NewspaperManager newspaperManager = null;
    private AuthorManager authorManager = null;
    private String LOG_TAG = "NewsReaderApplication";

    @Override
    public void onCreate() {

        super.onCreate();
        Log.i(this.LOG_TAG, "Started");

        this.newspaperManager = new NewspaperManager(getApplicationContext());
        this.authorManager = new AuthorManager(getApplicationContext());

    }

    public NewspaperManager getNewspaperManager() {
        return this.newspaperManager;
    }

    public AuthorManager getAuthorManager() {
        return this.authorManager;
    }
}
