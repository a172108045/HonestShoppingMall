package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.song.honestshoppingmall.fragment.ShopCartFragment;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.RetrofitUtil;
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

public class CardRecyclerAdapter extends RecyclerView.Adapter<CardRecyclerAdapter.MyViewHolder> implements View.OnClickListener{
    private  CheckBox                    mCb_card_checkall;
    private TextView                     mTvTotalPrice;
    private Context                      mContext;
    private List<SerchCardBean.CartBean> mData;
    private boolean checkedData[]  ;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            FragmentManager supportFragmentManager = ((HomeActivity) mContext).getSupportFragmentManager();
            ShopCartFragment shopCartFragment = (ShopCartFragment) supportFragmentManager.findFragmentByTag("ShopCartFragment");
            System.out.println(shopCartFragment);
            shopCartFragment.refreshData();
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
        view.setOnClickListener(this);
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

        holder.mCb_card.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               checkedData[position] = b;
                calculator();
            }
        });



        holder.mEt_number.setText(productCount+"");


        holder.mBtn_card_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNetDataAdd(productCount,productId);
                holder.mBtn_card_remove.setEnabled(true);

            }
        });
        holder.mBtn_card_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               holder.mBtn_card_add.setEnabled(true);
                updateNetDataRemove(productCount,productId);


            }
        });

        holder.mEt_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = holder.mEt_number.getText().toString();
                int number = Integer.parseInt(text);
                if (number<=0){
                    holder.mBtn_card_remove.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



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
        map.put("userId","20428");
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
        map.put("userId","20428");
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

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener !=null){
            mOnItemClickListener.onItemClick(view, (String) view.getTag());
        }
    }



    class MyViewHolder extends RecyclerView.ViewHolder {


       CheckBox mCb_card;
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
        void onItemClick(View view , String data);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
}
