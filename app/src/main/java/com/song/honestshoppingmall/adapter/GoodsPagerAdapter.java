package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.song.honestshoppingmall.util.Urls;

import java.util.ArrayList;
import java.util.List;

/**
 * @Creator Administrator
 * @CreatedDate 2017/1/11 17:13
 * @Description ${TODO}
 * @Updater $Author$
 * @UpdateTime $Date$
 * @UpdateDesc ${TODO}
 */

public class GoodsPagerAdapter extends PagerAdapter {
    private List<String> mDatas;
    private Context mContext;
    private final List<ImageView> mViewList = new ArrayList<>();

    public GoodsPagerAdapter(Context context, List<String> datas) {
        this.mContext = context;
        this.mDatas = datas;

        mViewList.clear();
        for (int i = 0; i < datas.size(); i++) {
            ImageView iv = new ImageView(mContext);
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            mViewList.add(iv);
        }
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = mViewList.get(position);
        Glide.with(mContext).load(Urls.BASE_URL + mDatas.get(position)).into(iv);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }
}
