package com.example.willing.zhihudaily.model;

import java.util.List;

/**
 * Created by Willing on 3/2/2016/002.
 */
public class BeforeStoriesEntity
{

    /**
     * date : 20131118
     * stories : [{"title":"深夜食堂 · 我的张曼妮","ga_prefix":"111822","images":["http://p4.zhimg.com/7b/c8/7bc8ef5947b069513c51e4b9521b5c82.jpg"],"type":0,"id":1747159},"..."]
     */

    private String date;
    /**
     * title : 深夜食堂 · 我的张曼妮
     * ga_prefix : 111822
     * images : ["http://p4.zhimg.com/7b/c8/7bc8ef5947b069513c51e4b9521b5c82.jpg"]
     * type : 0
     * id : 1747159
     */

    private List<StoryEntity> stories;

    public void setDate(String date) {
        this.date = date;
    }

    public void setStories(List<StoryEntity> stories) {
        this.stories = stories;
    }

    public String getDate() {
        return date;
    }

    public List<StoryEntity> getStories() {
        return stories;
    }


}
