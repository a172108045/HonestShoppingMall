package com.song.honestshoppingmall.bean;

import java.util.List;

/**
 * Created by yspc on 2017/1/11.
 */

public class PrommotionBean extends BaseBean{

    /**
     * id : 2
     * name : 儿童玩具聚划算
     * pic : /images/topic/2.jpg
     */

    private List<TopicBean> topic;

    public List<TopicBean> getTopic() {
        return topic;
    }

    public void setTopic(List<TopicBean> topic) {
        this.topic = topic;
    }

    public static class TopicBean {
        private int id;
        private String name;
        private String pic;

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

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
