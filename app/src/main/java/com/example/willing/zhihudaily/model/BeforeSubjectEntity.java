package com.example.willing.zhihudaily.model;

import java.util.List;

/**
 * Created by Willing on 3/2/2016/002.
 */
public class BeforeSubjectEntity
{

    /**
     * images : ["http://pic1.zhimg.com/0ccbd47b0cb755d0d7028f3e69f35c00_t.jpg"]
     * type : 2
     * id : 7173971
     * title : 发改委前三季度批复项目投资超3万亿
     */

    private List<StoryEntity> stories;

    public void setStories(List<StoryEntity> stories) {
        this.stories = stories;
    }

    public List<StoryEntity> getStories() {
        return stories;
    }


}
