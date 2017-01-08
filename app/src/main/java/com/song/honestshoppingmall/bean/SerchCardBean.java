package com.song.honestshoppingmall.bean;

import java.util.List;

/**
 * Created by lizhenquan on 2017/1/8.
 */

public class SerchCardBean extends BaseBean {

    /**
     * id : 3
     * product : {"buyLimit":10,"id":2,"name":"粉色毛衣","number":"13","pic":"/images/product/detail/q1.jpg","price":100}
     * productCount : 24
     * productId : 2
     * property : {"id":1,"k":"颜色","v":"红色"}
     * propertyId : 1
     * userId : 20428
     */

    private List<CartBean> cart;

    public List<CartBean> getCart() {
        return cart;
    }

    public void setCart(List<CartBean> cart) {
        this.cart = cart;
    }

    public static class CartBean {
        private int id;
        /**
         * buyLimit : 10
         * id : 2
         * name : 粉色毛衣
         * number : 13
         * pic : /images/product/detail/q1.jpg
         * price : 100
         */

        private ProductBean product;
        private int productCount;
        private int productId;
        /**
         * id : 1
         * k : 颜色
         * v : 红色
         */

        private PropertyBean property;
        private int propertyId;
        private int userId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public int getProductCount() {
            return productCount;
        }

        public void setProductCount(int productCount) {
            this.productCount = productCount;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public PropertyBean getProperty() {
            return property;
        }

        public void setProperty(PropertyBean property) {
            this.property = property;
        }

        public int getPropertyId() {
            return propertyId;
        }

        public void setPropertyId(int propertyId) {
            this.propertyId = propertyId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public static class ProductBean {
            private int    buyLimit;
            private int    id;
            private String name;
            private String number;
            private String pic;
            private int    price;

            public int getBuyLimit() {
                return buyLimit;
            }

            public void setBuyLimit(int buyLimit) {
                this.buyLimit = buyLimit;
            }

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

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
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

        public static class PropertyBean {
            private int    id;
            private String k;
            private String v;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getK() {
                return k;
            }

            public void setK(String k) {
                this.k = k;
            }

            public String getV() {
                return v;
            }

            public void setV(String v) {
                this.v = v;
            }
        }

        @Override
        public String toString() {
            return "CartBean{" +
                    "id=" + id +
                    ", product=" + product +
                    ", productCount=" + productCount +
                    ", productId=" + productId +
                    ", property=" + property +
                    ", propertyId=" + propertyId +
                    ", userId=" + userId +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SerchCardBean{" +
                "cart=" + cart
                +"response="+response
                +"error="+error
                +"error_code="+error_code
                +
                '}';
    }
}
