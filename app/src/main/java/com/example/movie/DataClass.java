package com.example.movie;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data")
public class DataClass {
    @PrimaryKey
    public int key;
    public String url;
    public String herourl;
    public String title;
    public String description;
    public String score;
    public boolean added;

    public DataClass(int key,String url, String herourl, String title, String score, String description, boolean added) {
        this.key=key;
        this.score = score;
        this.url = url;
        this.herourl = herourl;
        this.title = title;
        this.description = description;
        this.added=added;
    }

    public int getKey() {
        return key;
    }
    public String getScore() {
        return score;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHerourl() {
        return herourl;
    }

    public void setHerourl(String herourl) {
        this.herourl = herourl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
