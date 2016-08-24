package com.dod.newsreader.model;

import com.michaldabski.msqlite.Annotations;

import java.io.Serializable;

/**
 * Created by Deniz on 17.08.2016.
 */
public class Newspaper implements Serializable{
    @Annotations.PrimaryKey
    Integer id;
    @Annotations.ColumnName("newspaper_name")
    String name;
    @Annotations.ColumnName("newspaper_img")
    Integer resourceId;

    public Newspaper() {
    }

    public Newspaper(Integer id, String name , Integer resourceId) {
        this.id = id;
        this.name = name;
        this.resourceId = resourceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }
}
