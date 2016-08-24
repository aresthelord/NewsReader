package com.dod.newsreader.model;

import com.michaldabski.msqlite.Annotations;

import java.io.Serializable;

/**
 * Created by Deniz on 17.08.2016.
 */
public class Author implements Serializable{

    @Annotations.PrimaryKey
    Integer id;
    @Annotations.ColumnName("author_name")
    String name;
    @Annotations.ColumnName("newspaper_id")
    Integer newspaper_id;
    @Annotations.ColumnName("author_img")
    Integer resourceId;
    public Author() {
    }

    public Author(Integer id, String name,Integer resourceId, Integer newspaperId) {
        this.id = id;
        this.name = name;
        this.resourceId = resourceId;
        this.newspaper_id = newspaperId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNewspaper_id() {
        return newspaper_id;
    }

    public void setNewspaper_id(Integer newspaperId) {
        this.newspaper_id = newspaperId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }
}
