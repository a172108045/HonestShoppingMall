package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.ShopCategoryBean;
import com.song.honestshoppingmall.util.Urls;

import java.util.List;

/**
 * @Creator Administrator
 * @CreatedDate 2017/1/9 22:04
 * @Description ${TODO}
 * @Updater $Author$
 * @UpdateTime $Date$
 * @UpdateDesc ${TODO}
 */

public class CategoryThirdAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<ShopCategoryBean.CategoryBean> mDatas;

    public CategoryThirdAdapter(Context context, List<ShopCategoryBean.CategoryBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryThirdAdapter.ViewHolder viewHolder;

        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.item_category_third, null);
            viewHolder = new CategoryThirdAdapter.ViewHolder();

            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_item_category_third);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv_item_category_third);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (CategoryThirdAdapter.ViewHolder) convertView.getTag();
        }

        ShopCategoryBean.CategoryBean categoryBean = mDatas.get(position);
        viewHolder.tv.setText(categoryBean.getName());
        Glide.with(mContext).load(Uri.parse(Urls.BASE_URL + categoryBean.getPic())).into(viewHolder.iv);

        return convertView;
    }

    private class ViewHolder{
        public TextView tv;
        public ImageView iv;
    }
}
