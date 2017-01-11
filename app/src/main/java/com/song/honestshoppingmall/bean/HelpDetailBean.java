package com.song.honestshoppingmall.bean;

import java.util.List;

/**
 * Created by Judy on 2017/1/11.
 */

public class HelpDetailBean extends BaseBean {

    /**
     * content : 不退
     * title : 购买的商品如何退货？
     */

    private List<HelpDetailListBean> helpDetailList;

    public List<HelpDetailListBean> getHelpDetailList() {
        return helpDetailList;
    }

    public void setHelpDetailList(List<HelpDetailListBean> helpDetailList) {
        this.helpDetailList = helpDetailList;
    }

    public static class HelpDetailListBean {
        private String content;
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
