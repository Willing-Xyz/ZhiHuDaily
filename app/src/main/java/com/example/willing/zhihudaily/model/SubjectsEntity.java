package com.example.willing.zhihudaily.model;

import java.util.List;

/**
 * Created by Willing on 3/2/2016/002.
 */
public class SubjectsEntity {


    /**
     * color : 8307764
     * thumbnail : http://pic4.zhimg.com/2c38a96e84b5cc8331a901920a87ea71.jpg
     * description : 内容由知乎用户推荐，海纳主题百万，趣味上天入地
     * id : 12
     * name : 用户推荐日报
     */

    private List<OthersEntity> others;

    public void setOthers(List<OthersEntity> others) {
        this.others = others;
    }

    public List<OthersEntity> getOthers() {
        return others;
    }

    public static class OthersEntity {
        private String thumbnail;
        private String description;
        private int id;
        private String name;

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public String getDescription() {
            return description;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
