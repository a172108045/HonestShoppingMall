package com.song.honestshoppingmall.bean;

import java.util.List;

/**
 * Created by lizhenquan on 2017/1/8.
 */

public class HomeMsgBean extends  BaseBean{

    /**
     * homeTopic : [{"id":123,"pic":"/images/home/image1.jpg","title":"活动1"},{"id":124,"pic":"/images/home/image2.jpg","title":"活动2"},{"id":125,"pic":"/images/home/image3.jpg","title":"活动3"},{"id":126,"pic":"/images/home/image4.jpg","title":"活动1"},{"id":127,"pic":"/images/home/image5.jpg","title":"活动2"},{"id":128,"pic":"/images/home/image6.jpg","title":"活动3"},{"id":129,"pic":"/images/home/wawa.jpg","title":"充气娃娃"}]
     * response : home
     */

    /**
     * id : 123
     * pic : /images/home/image1.jpg
     * title : 活动1
     */

    private List<HomeTopicBean> homeTopic;


    public List<HomeTopicBean> getHomeTopic() {
        return homeTopic;
    }

    public void setHomeTopic(List<HomeTopicBean> homeTopic) {
        this.homeTopic = homeTopic;
    }

    public static class HomeTopicBean {
        private int    id;
        private String pic;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    @Override
    public String toString() {
        return "HomeMsgBean{" +
                "homeTopic=" + homeTopic
                +"response="+response
                +"error="+error
                +"error_code="+error_code
                +
                '}';
    }
}
