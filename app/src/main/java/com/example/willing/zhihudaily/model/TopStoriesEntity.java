package com.example.willing.zhihudaily.model;

/**
 * Created by Willing on 3/2/2016/002.
 */
public class TopStoriesEntity {
    private String title;
    private String image;
    private int type;
    private int id;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}
