package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.ShopCategoryBean;

import java.util.List;

/**
 * @Creator Administrator
 * @CreatedDate 2017/1/9 17:02
 * @Description ${TODO}
 * @Updater $Author$
 * @UpdateTime $Date$
 * @UpdateDesc ${TODO}
 */

public class CategoryLeftAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<ShopCategoryBean.CategoryBean> mDatas;

    public CategoryLeftAdapter(Context context, List<ShopCategoryBean.CategoryBean> datas) {
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
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.item_category_left, null);
            viewHolder = new ViewHolder();

            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_item_category_left);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ShopCategoryBean.CategoryBean categoryBean = mDatas.get(position);
        viewHolder.tv.setText(categoryBean.getName());
//        viewHolder.tv.setSelected(false);

        return convertView;
    }

    private class ViewHolder{
        public TextView tv;
    }
}
