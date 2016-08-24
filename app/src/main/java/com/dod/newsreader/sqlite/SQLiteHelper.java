/*******************************************************************************
 * Copyright (c) 2014 Michal Dabski
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/
package com.dod.newsreader.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.michaldabski.msqlite.MSQLiteOpenHelper;
import com.dod.newsreader.R;
import com.dod.newsreader.model.Author;
import com.dod.newsreader.model.Newspaper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends MSQLiteOpenHelper {
    private String LOG_TAG ="SQLiteHelper";
    private static final String DB_NAME = "newsreader.db";
    private static final int DB_VERSION = 2;

    private final Context context;

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION,
                new Class[]{Newspaper.class, Author.class});
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);

        List<Newspaper> newspapers = new ArrayList<Newspaper>();

        newspapers.add(new Newspaper(1, "Sözcü" , R.drawable.sozcu_logo));
        newspapers.add(new Newspaper(2, "Vatan" , R.drawable.vatan_logo));
        newspapers.add(new Newspaper(3, "Milliyet" , R.drawable.milliyet_logo));
        newspapers.add(new Newspaper(4, "Takvim" , R.drawable.takvim_logo));
        newspapers.add(new Newspaper(5, "Türkiye" , R.drawable.turkiye_logo));
        newspapers.add(new Newspaper(6, "Taraf" , R.drawable.taraf_logo));
        newspapers.add(new Newspaper(7, "Star" , R.drawable.star_logo));

        for (Newspaper newspaper : newspapers) {
            insert(db, newspaper);
            Log.i(this.LOG_TAG , "added newspaper : " + newspaper.getName());

        }

        List<Author> authors = new ArrayList<Author>();
        authors.add(new Author(1,"Yılmaz ÖZDİL" ,R.drawable.yilmaz_ozdil,1));
        authors.add(new Author(2,"Ahmet Hakan",R.drawable.ahmet_hakan,1));
        authors.add(new Author(3,"Vatan Yazar ?",R.drawable.sabah_yazar,2));
        for(Author author : authors){
            insert(db, author);
            Log.i(this.LOG_TAG, "added author : " + author.getName());
        }

    }

    protected String getString(int res) {
        return context.getString(res);
    }

}
