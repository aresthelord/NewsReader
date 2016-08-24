package com.dod.newsreader.sqlite;

import android.content.Context;
import android.util.Log;

import com.dod.newsreader.model.Author;
import com.dod.newsreader.model.Newspaper;

import java.util.List;

/**
 * Created by Deniz on 18.08.2016.
 */
public class AuthorManager {

    private List<Author> allAuthors;
    private final SQLiteHelper sqLiteHelper;
    private String LOG_TAG = "AuthorManager";

    public AuthorManager(Context context)
    {
        this.sqLiteHelper = new SQLiteHelper(context);


    }

    public List<Author> getAllAuthors() {
       if(this.allAuthors == null){
           this.allAuthors = sqLiteHelper.selectAll(Author.class);
           Log.i(this.LOG_TAG , this.allAuthors != null ? "Yazarlar veritabanında : "+this.allAuthors : "Yazar Yok");
       }
        return this.allAuthors;
    }
    public List<Author> getAuthorByNewspaper(Newspaper newspaper){
        return sqLiteHelper.select(Author.class, " newspaper_id = ? " , new String[]{String.valueOf(newspaper.getId())} ,null ,null );
    }

}
