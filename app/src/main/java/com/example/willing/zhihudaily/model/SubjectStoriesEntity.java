package com.example.willing.zhihudaily.model;

import java.util.List;

/**
 * Created by Willing on 3/2/2016/002.
 */
public class SubjectStoriesEntity {


    /**
     * stories : [{"images":["http://pic1.zhimg.com/84dadf360399e0de406c133153fc4ab8_t.jpg"],"type":0,"id":4239728,"title":"前苏联监狱纹身百科图鉴"},"..."]
     * description : 为你发现最有趣的新鲜事，建议在 WiFi 下查看
     * background : http://pic1.zhimg.com/a5128188ed788005ad50840a42079c41.jpg
     * color : 8307764
     * name : 不许无聊
     * image : http://pic3.zhimg.com/da1fcaf6a02d1223d130d5b106e828b9.jpg
     * editors : [{"url":"http://www.zhihu.com/people/wezeit","bio":"微在 Wezeit 主编","id":70,"avatar":"http://pic4.zhimg.com/068311926_m.jpg","name":"益康糯米"},"..."]
     * image_source :
     */

    private String description;
    private String background;
    private String name;
    private String image;
    private String image_source;
    /**
     * images : ["http://pic1.zhimg.com/84dadf360399e0de406c133153fc4ab8_t.jpg"]
     * type : 0
     * id : 4239728
     * title : 前苏联监狱纹身百科图鉴
     */

    private List<StoryEntity> stories;
    /**
     * url : http://www.zhihu.com/people/wezeit
     * bio : 微在 Wezeit 主编
     * id : 70
     * avatar : http://pic4.zhimg.com/068311926_m.jpg
     * name : 益康糯米
     */

    private List<EditorsEntity> editors;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public void setStories(List<StoryEntity> stories) {
        this.stories = stories;
    }

    public void setEditors(List<EditorsEntity> editors) {
        this.editors = editors;
    }

    public String getDescription() {
        return description;
    }

    public String getBackground() {
        return background;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getImage_source() {
        return image_source;
    }

    public List<StoryEntity> getStories() {
        return stories;
    }

    public List<EditorsEntity> getEditors() {
        return editors;
    }



    public static class EditorsEntity {
        private String url;
        private String bio;
        private int id;
        private String avatar;
        private String name;

        public void setUrl(String url) {
            this.url = url;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public String getBio() {
            return bio;
        }

        public int getId() {
            return id;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getName() {
            return name;
        }
    }
}
