package com.example.willing.zhihudaily.model;

import java.util.List;

/**
 * Created by Willing on 3/2/2016/002.
 */
public class StoryEntity {


    /**
     * title : 中国古代家具发展到今天有两个高峰，一个两宋一个明末（多图）
     * ga_prefix : 052321
     * images : ["http://p1.zhimg.com/45/b9/45b9f057fc1957ed2c946814342c0f02.jpg"]
     * type : 0
     * id : 3930445
     */

    private String title;
    private int type;
    private int id;
    private List<String> images;

    private int itemType;

    public void setItemType(int type)
    {
        itemType = type;
    }

    public int getItemType()
    {
        return itemType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public List<String> getImages() {
        return images;
    }
}
