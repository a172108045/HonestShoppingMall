package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.RotateBean;

import java.util.List;

/**
 * Created by yspc on 2017/1/8.
 */

public class RotateVpAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<RotateBean> datas;

    public RotateVpAdapter(List<RotateBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public RotateVpAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<RotateBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        int newPosition = position % datas.size();
        View convertView = mInflater.inflate(R.layout.item_vp, container, false);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.item_iv);
        TextView textView = (TextView) convertView.findViewById(R.id.item_tv);
        imageView.setImageResource(datas.get(newPosition).getImgId());
        container.addView(convertView);
        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
