package com.song.honestshoppingmall.util;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by lizhenquan on 2017/1/10.
 */

public class ScreenUtils {

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }
}
