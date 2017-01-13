package com.song.honestshoppingmall.bean;

import java.util.List;

/**
 * Created by Judy on 2017/1/13.
 */

public class ProductCommentBean extends BaseBean {

    /**
     * content : 裙子不错
     * time : 2014
     * title : 裙子
     * username : 赵日天
     */

    private List<CommentBean> comment;

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public static class CommentBean {
        private String content;
        private int time;
        private String title;
        private String username;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
