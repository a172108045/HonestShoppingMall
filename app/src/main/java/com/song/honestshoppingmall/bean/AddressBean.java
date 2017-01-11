package com.song.honestshoppingmall.bean;

import java.util.List;

/**
 * Created by Judy on 2017/1/11.
 */

public class AddressBean extends BaseBean {

    /**
     * addressArea : abc
     * addressDetail : abc
     * city : abc
     * id : 133
     * isDefault : 0
     * name : abc
     * phoneNumber : 123
     * province : abc
     * zipCode : 12345
     */

    private List<AddressListBean> addressList;

    public List<AddressListBean> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressListBean> addressList) {
        this.addressList = addressList;
    }

    public static class AddressListBean {
        private String addressArea;
        private String addressDetail;
        private String city;
        private int id;
        private int isDefault;
        private String name;
        private String phoneNumber;
        private String province;
        private String zipCode;

        public String getAddressArea() {
            return addressArea;
        }

        public void setAddressArea(String addressArea) {
            this.addressArea = addressArea;
        }

        public String getAddressDetail() {
            return addressDetail;
        }

        public void setAddressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }
}
