package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.SerchCardBean;
import com.song.honestshoppingmall.util.Urls;

import java.util.List;

/**
 * Created by lizhenquan on 2017/1/8.
 */

public class CardRecyclerAdapter extends RecyclerView.Adapter<CardRecyclerAdapter.MyViewHolder> {
    private Context                      mContext;
    private List<SerchCardBean.CartBean> mData;

    public CardRecyclerAdapter(Context context, SerchCardBean body) {
        this.mContext = context;
        this.mData = body.getCart();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.recycle_card_item, parent,
                false));
        return holder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SerchCardBean.CartBean cartBean = mData.get(position);
        SerchCardBean.CartBean.PropertyBean property = cartBean.getProperty();
        SerchCardBean.CartBean.ProductBean product = cartBean.getProduct();

//        holder.mIv_card.setImageURI(Uri.parse(Urls.BASE_URL + product.getPic()));
        Glide.with(mContext.getApplicationContext()).load(Urls.BASE_URL + product.getPic()).into(holder.mIv_card);
        holder.mTv_card_color.setText("颜色：" + property.getV());
        holder.mTv_card_size.setText("尺码：" + product.getNumber());
        holder.mTv_card_price.setText("$" + product.getPrice());
        holder.mBtn_card_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = holder.mEt_number.getText().toString();
                int i = Integer.parseInt(text);
                holder.mEt_number.setText(i+1+"");
            }
        });
        holder.mBtn_card_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = holder.mEt_number.getText().toString();
                int i = Integer.parseInt(text);
                holder.mEt_number.setText(i-1+"");
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder {


        CheckBox  mCb_card;
        ImageView mIv_card;
        TextView  mTv_card_color;
        TextView  mTv_card_size;
        TextView  mTv_card_price;
        private final Button   mBtn_card_add;
        private final Button   mBtn_card_remove;
        private final EditText mEt_number;

        public MyViewHolder(View view) {
            super(view);

            mCb_card = (CheckBox) view.findViewById(R.id.cb_card);
            mIv_card = (ImageView) view.findViewById(R.id.iv_card);
            mTv_card_color = (TextView) view.findViewById(R.id.tv_card_color);
            mTv_card_size = (TextView) view.findViewById(R.id.tv_card_size);
            mTv_card_price = (TextView) view.findViewById(R.id.tv_card_price);
            mBtn_card_add = (Button) view.findViewById(R.id.btn_card_add);
            mBtn_card_remove = (Button) view.findViewById(R.id.btn_card_remove);
            mEt_number = (EditText) view.findViewById(R.id.et_number);

        }

    }
}
