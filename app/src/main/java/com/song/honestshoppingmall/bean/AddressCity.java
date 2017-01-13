package com.song.honestshoppingmall.bean;

import java.util.List;

/**
 * Created by zan on 2017/1/13.
 */

public class AddressCity extends BaseBean {

    /**
     * id : 2
     * value : 武汉市
     */

    private List<AreaListBean> areaList;

    public List<AreaListBean> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaListBean> areaList) {
        this.areaList = areaList;
    }

    public static class AreaListBean {
        private int id;
        private String value;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
