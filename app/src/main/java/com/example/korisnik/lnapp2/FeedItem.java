package com.example.korisnik.lnapp2;

import java.util.ArrayList;
import java.util.List;

public class FeedItem {
    String title;
    String link;
    String description;
    String pubDate;
    ArrayList<String> thumbnailUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public ArrayList<String> getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(ArrayList<String> thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}

