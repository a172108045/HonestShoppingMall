package com.song.honestshoppingmall.bean;

/**
 * Created by zan on 2017/1/10.
 */

public class RegisterBean extends BaseBean {
    /**
     * userid : 2016
     */

    private UserInfoBean userInfo;

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {
        private String userid;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }


}
