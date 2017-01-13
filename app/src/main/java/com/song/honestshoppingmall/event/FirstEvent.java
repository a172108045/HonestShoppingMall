package com.song.honestshoppingmall.event;

/**
 * Created by lizhenquan on 2017/1/7.
 */

public class FirstEvent {
    private  int mMsg;

    public FirstEvent(int msg) {
        mMsg = msg;
    }
    public int getMsg(){
        return mMsg;
    }
}
