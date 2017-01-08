package com.song.honestshoppingmall.bean;

/**
 * Created by lizhenquan on 2017/1/8.
 */

public class LoginBean extends BaseBean{

    /**
     * response : login
     * userInfo : {"userid":"20428"}
     */

    /**
     * userid : 20428
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

    @Override
    public String toString() {
        return "LoginBean{" +
                "userInfo=" + userInfo
                +"response="+response
                +"error="+error
                +"error_code="+error_code
                +
                '}';
    }
}
