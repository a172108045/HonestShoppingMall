package com.song.honestshoppingmall.bean;

import java.util.List;

/**
 * Created by lizhenquan on 2017/1/10.
 */

public class SearchRecommandBean extends BaseBean {

    private List<String> searchKeywords;

    public List<String> getSearchKeywords() {
        return searchKeywords;
    }

    public void setSearchKeywords(List<String> searchKeywords) {
        this.searchKeywords = searchKeywords;
    }
}
