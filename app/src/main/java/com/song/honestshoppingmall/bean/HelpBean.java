package com.song.honestshoppingmall.bean;

import java.util.List;

/**
 * Created by Judy on 2017/1/11.
 */

public class HelpBean extends BaseBean {

    /**
     * id : 1
     * title : 购物指南
     */

    private List<HelpListBean> helpList;

    public List<HelpListBean> getHelpList() {
        return helpList;
    }

    public void setHelpList(List<HelpListBean> helpList) {
        this.helpList = helpList;
    }

    public static class HelpListBean {
        private int id;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
