package com.song.honestshoppingmall.bean;

import java.util.List;

/**
 * @Creator Administrator
 * @CreatedDate 2017/1/14 9:55
 * @Description ${TODO}
 * @Updater $Author$
 * @UpdateTime $Date$
 * @UpdateDesc ${TODO}
 */

public class CollectionBean extends BaseBean {

    /**
     * listCount : 4
     * productList : [{"id":1,"marketPrice":500,"name":"韩版时尚蕾丝裙","pic":"/images/product/detail/c3.jpg","price":350},{"id":2,"marketPrice":180,"name":"粉色毛衣","pic":"/images/product/detail/q1.jpg","price":100},{"id":5,"marketPrice":98,"name":"时尚女裙","pic":"/images/product/detail/a1.jpg","price":108},{"id":28,"marketPrice":300,"name":"春装新款","pic":"/images/product/detail/q26.jpg","price":200}]
     */

    private int listCount;
    /**
     * id : 1
     * marketPrice : 500
     * name : 韩版时尚蕾丝裙
     * pic : /images/product/detail/c3.jpg
     * price : 350
     */

    private List<ProductListBean> productList;

    public int getListCount() {
        return listCount;
    }

    public void setListCount(int listCount) {
        this.listCount = listCount;
    }

    public List<ProductListBean> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductListBean> productList) {
        this.productList = productList;
    }

    public static class ProductListBean {
        private int id;
        private int marketPrice;
        private String name;
        private String pic;
        private int price;

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
