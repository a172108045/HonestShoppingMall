package com.song.honestshoppingmall.bean;

import java.util.List;

/**
 * Created by Judy on 2017/1/8.
 */

public class MyOrderBean extends BaseBean {

    /**
     * flag : 1
     * orderId : 260092
     * price : 208
     * status : 未处理
     * time : 1439528260115
     */

    private List<OrderListBean> orderList;

    public List<OrderListBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderListBean> orderList) {
        this.orderList = orderList;
    }

    public static class OrderListBean {
        private int flag;
        private String orderId;
        private int price;
        private String status;
        private String time;

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "OrderListBean{" +
                    "flag=" + flag +
                    ", orderId='" + orderId + '\'' +
                    ", price=" + price +
                    ", status='" + status + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MyOrderBean{" +
                "orderList=" + orderList
                +"response="+response
                +"error="+error
                +"error_code="+error_code
                +
                '}';
    }
}
