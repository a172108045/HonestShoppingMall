package com.song.honestshoppingmall.bean;

import java.util.List;

/**
 * Created by lizhenquan on 2017/1/10.
 */

public class SearchDetailBean extends BaseBean {

    /**
     * id : 29
     * marketPrice : 180
     * name : 日本奶粉
     * pic : /images/product/detail/q26.jpg
     * price : 160
     */

    private List<ProductListBean> productList;

    public List<ProductListBean> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductListBean> productList) {
        this.productList = productList;
    }

    public static class ProductListBean {
        private int    id;
        private int    marketPrice;
        private String name;
        private String pic;
        private int    price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(int marketPrice) {
            this.marketPrice = marketPrice;
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
