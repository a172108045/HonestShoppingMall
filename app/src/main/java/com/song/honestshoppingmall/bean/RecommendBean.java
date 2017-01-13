package com.song.honestshoppingmall.bean;

import java.util.List;

/**
 * Created by yspc on 2017/1/13.
 */

public class RecommendBean extends BaseBean {

    /**
     * id : 1
     * key : 孕妈专区
     * value : [{"id":1218,"name":"雅培","pic":"/images/brand/c.png"},{"id":1219,"name":"贝因美","pic":"/images/brand/d.png"},{"id":1220,"name":"周生生","pic":"/images/brand/a.png"},{"id":1221,"name":"婴姿坊","pic":"/images/brand/e.png"}]
     */

    private List<BrandBean> brand;

    public List<BrandBean> getBrand() {
        return brand;
    }

    public void setBrand(List<BrandBean> brand) {
        this.brand = brand;
    }

    public static class BrandBean {
        private int id;
        private String key;
        /**
         * id : 1218
         * name : 雅培
         * pic : /images/brand/c.png
         */

        private List<ValueBean> value;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<ValueBean> getValue() {
            return value;
        }

        public void setValue(List<ValueBean> value) {
            this.value = value;
        }

        public static class ValueBean {
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
}
