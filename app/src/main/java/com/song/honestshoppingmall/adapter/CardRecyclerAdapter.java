package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.bean.SerchCardBean;
import com.song.honestshoppingmall.bean.UpDateCartBean;
import com.song.honestshoppingmall.fragment.GoodsDetailsFragment;
import com.song.honestshoppingmall.fragment.ShopCartFragment;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.Constants;
import com.song.honestshoppingmall.util.RetrofitUtil;
import com.song.honestshoppingmall.util.SpUtil;
import com.song.honestshoppingmall.util.Urls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lizhenquan on 2017/1/8.
 */

public class CardRecyclerAdapter extends RecyclerView.Adapter<CardRecyclerAdapter.MyViewHolder> {
    private  CheckBox                    mCb_card_checkall;
    private TextView                     mTvTotalPrice;
    private Context                      mContext;
    private List<SerchCardBean.CartBean> mData;
    private int TrueCount = 0;
    public boolean checkedData[]  ;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    FragmentManager supportFragmentManager = ((HomeActivity) mContext).getSupportFragmentManager();
                    ShopCartFragment shopCartFragment = (ShopCartFragment) supportFragmentManager.findFragmentByTag("ShopCartFragment");
                    shopCartFragment.refreshData();
                    break;
                case 1:
                    mCb_card_checkall.setChecked(true);
                    break;
                case 2:
                    mCb_card_checkall.setChecked(false);
                    break;

            }

        }
    };
    public CardRecyclerAdapter(Context context, List<SerchCardBean.CartBean> body, TextView tv_totalPrice, CheckBox cb_card_checkall) {
        this.mContext = context;
        this.mData = body;
        this.mTvTotalPrice = tv_totalPrice;
        this.mCb_card_checkall = cb_card_checkall;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_card_item, parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SerchCardBean.CartBean cartBean = mData.get(position);
        checkedData = new boolean[mData.size()];
        SerchCardBean.CartBean.PropertyBean property = cartBean.getProperty();
        final int productCount = cartBean.getProductCount();
        final int productId = cartBean.getProductId();
        final SerchCardBean.CartBean.ProductBean product = cartBean.getProduct();

        Glide.with(mContext.getApplicationContext()).load(Urls.BASE_URL + product.getPic()).into(holder.mIv_card);
        holder.mTv_card_color.setText("颜色：" + property.getV());
        holder.mTv_card_size.setText("      尺码：" + product.getNumber());
        holder.mTv_card_price.setText("$" + product.getPrice()*productCount);
        holder.mTv_product_card_name.setText(product.getName());
        holder.mCb_card.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               checkedData[position] = b;
                calculator();
            }
        });

        holder.myitemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("pId",mData.get(position).getProduct().getId());
                ((HomeActivity) mContext).changeFragment(new GoodsDetailsFragment(),"GoodsDetailsFragment",bundle);
            }
        });


        holder.mEt_number.setText(productCount+"");
        holder.mBtn_card_remove.setEnabled(Integer.parseInt(holder.mEt_number.getText().toString()) > 0);
        holder.mBtn_card_add.setEnabled(Integer.parseInt(holder.mEt_number.getText().toString()) < product.getBuyLimit());

        holder.mBtn_card_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNetDataAdd(productCount,productId);

            }
        });
        holder.mBtn_card_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNetDataRemove(productCount,productId);
            }
        });

        String text = holder.mEt_number.getText().toString();
        if (TextUtils.equals(text,"1")){
            holder.mBtn_card_remove.setEnabled(true);
        }


        if (mCb_card_checkall.isChecked()){
            for (int i = 0; i < getItemCount(); i++) {
                holder.mCb_card.setChecked(true);
                checkedData[i] = true;
                calculator();
            }
        }else{
            for (int i = 0; i < getItemCount(); i++) {
                holder.mCb_card.setChecked(false);
                checkedData[i] = false;
                calculator();
            }
        }
    }

    private void updateNetDataAdd(int productCount, int productId) {

        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        Map<String,String> map = new HashMap<>();
        map.put("userId",SpUtil.getString(mContext, Constants.USERID,""));
        map.put("productId",productId+"");
        map.put("productCount",productCount+1+"");
        map.put("propertyId","1");
        apiRetrofitInstance.getUpdateCart(map).enqueue(new Callback<UpDateCartBean>() {
            @Override
            public void onResponse(Call<UpDateCartBean> call, Response<UpDateCartBean> response) {
                if (response.isSuccessful()) {

                    UpDateCartBean body = response.body();
                    if (body.error==null){
                        handler.sendEmptyMessage(0);
                    }else{
                        Toast.makeText(mContext, body.error, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpDateCartBean> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateNetDataRemove(int productCount, int productId) {

        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        Map<String,String> map = new HashMap<>();
        map.put("userId", SpUtil.getString(mContext, Constants.USERID,""));
        map.put("productId",productId+"");
        map.put("productCount",productCount-1+"");
        map.put("propertyId","1");
        apiRetrofitInstance.getUpdateCart(map).enqueue(new Callback<UpDateCartBean>() {
            @Override
            public void onResponse(Call<UpDateCartBean> call, Response<UpDateCartBean> response) {
                if (response.isSuccessful()) {

                    UpDateCartBean body = response.body();
                    if (body.error==null){
                        handler.sendEmptyMessage(0);
                    }else{
                        Toast.makeText(mContext, body.error, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpDateCartBean> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder {


        private  View myitemview;
        CheckBox mCb_card;
        ImageView mIv_card;
        TextView  mTv_card_color;
        TextView  mTv_card_size;
        TextView  mTv_card_price;
        private  Button   mBtn_card_add;
        private  Button   mBtn_card_remove;
        private  EditText mEt_number;
        private  TextView mTv_product_card_name;

        public MyViewHolder(View view) {
            super(view);
            this.myitemview = view;
            mCb_card = (CheckBox) view.findViewById(R.id.cb_card);
            mIv_card = (ImageView) view.findViewById(R.id.iv_card);
            mTv_card_color = (TextView) view.findViewById(R.id.tv_card_color);
            mTv_card_size = (TextView) view.findViewById(R.id.tv_card_size);
            mTv_card_price = (TextView) view.findViewById(R.id.tv_card_price);
            mBtn_card_add = (Button) view.findViewById(R.id.btn_card_add);
            mBtn_card_remove = (Button) view.findViewById(R.id.btn_card_remove);
            mEt_number = (EditText) view.findViewById(R.id.et_number);
            mTv_product_card_name = (TextView) view.findViewById(R.id.tv_product_card_name);
        }

    }

    public void calculator() {

        int totalPrice = 0;
        for (int i = 0; i < mData.size(); i++) {
            if (checkedData[i]){

                totalPrice += mData.get(i).getProductCount()*mData.get(i).getProduct().getPrice();

            }
            mTvTotalPrice.setText("合计："+totalPrice);
        }

    }


    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int data);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
}
