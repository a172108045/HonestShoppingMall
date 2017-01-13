package com.song.honestshoppingmall.util;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.bean.AddCartBean;
import com.song.honestshoppingmall.bean.GoodsBean;
import com.song.honestshoppingmall.fragment.CheckOutFragment;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.song.honestshoppingmall.R.id.btn_card_add;
import static com.song.honestshoppingmall.R.id.btn_card_remove;
import static com.song.honestshoppingmall.R.id.btn_card_tianjia;
import static com.song.honestshoppingmall.R.id.iv_carddialog_dismiss;
import static com.song.honestshoppingmall.R.id.tv_price;


/**
 * Created by lizhenquan on 2017/1/7.
 */

public class DialogAlertUtils implements View.OnClickListener {
    private static AlertDialog           dialog;
    private final boolean mIsAddCart;
    private        Context               mContext;
    private        GoodsBean.ProductBean mData;
    private        Button                mBtn_card_remove;
    private        Button                mBtn_card_add;
    private        Button                mBtn_card_tianjia;
    private        EditText              mNumber;
    private        TextView              mTv_price;
    private        RadioButton           mRb_carddialog_color1;
    private        RadioButton           mRb_carddialog_size1;
    private        ImageView             mIv_carddialog_dismiss;
    private        ImageView             mIv_title;
    private        TextView              mTv_title;
    private        RadioGroup            mRg_dilog;


    public DialogAlertUtils(Context context, GoodsBean.ProductBean productBean, boolean isAddCart) {
        this.mContext = context;
        this.mData = productBean;
        this.mIsAddCart = isAddCart;
        init();
    }


    /**
     * 初始化操作
     */
    private void init() {
        initView();
        initEvent();
        initData();
    }

    /**
     * 初始化布局上的控件
     */
    private void initView() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        // 创建对话框
        dialog = builder.create();
        // 弹出对话框
        dialog.show();
        // 以下两行代码是对话框的EditText点击后不能显示输入法的
        dialog.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
        );
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_shopcar);
        window.setGravity(Gravity.BOTTOM);

        findView(window);

        if (mIsAddCart) {
            mBtn_card_tianjia.setText("添加");
        } else {
            mBtn_card_tianjia.setText("购买");
        }
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        mRb_carddialog_color1.setChecked(true);
        mRb_carddialog_size1.setChecked(true);

        mIv_carddialog_dismiss.setOnClickListener(this);
        mBtn_card_remove.setOnClickListener(this);
        mBtn_card_add.setOnClickListener(this);
        mBtn_card_tianjia.setOnClickListener(this);


        computeTotalPrice(mNumber, mTv_price);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        refreshDialog();
    }

    private void findView(Window window) {
        mBtn_card_remove = (Button) window.findViewById(btn_card_remove);
        mBtn_card_add = (Button) window.findViewById(btn_card_add);
        mBtn_card_tianjia = (Button) window.findViewById(btn_card_tianjia);
        mNumber = (EditText) window.findViewById(R.id.et_number);
        mTv_price = (TextView) window.findViewById(tv_price);
        mRg_dilog = (RadioGroup) window.findViewById(R.id.rg_dilog);
        mRb_carddialog_color1 = (RadioButton) window.findViewById(R.id.rb_carddialog_color1);
        mTv_title = (TextView) window.findViewById(R.id.tv_title);
        mRb_carddialog_size1 = (RadioButton) window.findViewById(R.id.rb_carddialog_size1);
        mIv_title = (ImageView) window.findViewById(R.id.iv_title);
        mIv_carddialog_dismiss = (ImageView) window.findViewById(iv_carddialog_dismiss);

    }

    public void refreshDialog() {
        setProductImage(mData.getPics().get(0));
        setProductName(mData.getName());
        setProductPrice(mData.getPrice());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_carddialog_dismiss:
                dismissScanNumberDialog();

                break;
            case R.id.btn_card_add: //点击商品数量+1
                add();

                break;
            case R.id.btn_card_remove://点击商品数量-1
                remove();
                break;
            case btn_card_tianjia://点击添加到数据库
                if (mIsAddCart) {
                    sendGetRequest();
                } else {
                    StringBuilder sku = new StringBuilder();
                    int id = mData.getId();
                    String number = mNumber.getText().toString();
                    sku.append(id).append(":").append(number).append(":").append("1").append(",").append("3");
                    Bundle bundle = new Bundle();
                    bundle.putString("sku", sku.toString());
                    ((HomeActivity)mContext).changeFragment(new CheckOutFragment(), "CheckOutFragment", bundle);
                    DialogAlertUtils.dismissScanNumberDialog();
                }
                break;
        }

    }

    private void remove() {
        mBtn_card_add.setEnabled(true);
        String num = mNumber.getText().toString();
        int i = Integer.parseInt(num);
        mNumber.setText(i - 1 + "");
        if (i <= 1) {
            mBtn_card_remove.setEnabled(false);
        }
    }

    /**
     * 点击+号数量加一
     */
    private void add() {
        mBtn_card_remove.setEnabled(true);
        String num = mNumber.getText().toString();
        int i = Integer.parseInt(num);
        mNumber.setText(i + 1 + "");
        if (i > mData.getBuyLimit()) {
            mBtn_card_add.setEnabled(false);
            Toast.makeText(mContext, "购买数量超出限制！", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendGetRequest() {
        //发送get请求
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", SpUtil.getString(mContext, Constants.USERID, ""));
        map.put("productId", mData.getId() + "");
        map.put("productCount", getProductCount() + "");
        map.put("propertyId", getPropertyId() + "");

        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        apiRetrofitInstance.getAddCartBean(map).enqueue(new Callback<AddCartBean>() {
            @Override
            public void onResponse(Call<AddCartBean> call, Response<AddCartBean> response) {
                if (response.isSuccessful()) {
                    if (response.body().error == null) {
                        Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();

                        dismissScanNumberDialog();
                    } else {
                        Toast.makeText(mContext, response.body().error, Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<AddCartBean> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getPropertyId() {
        for (int i = 0; i < mRg_dilog.getChildCount(); i++) {
            RadioButton view = (RadioButton) mRg_dilog.getChildAt(i);

            if (view.isChecked()) {
                return i + 1;
            }
        }
        return 1;
    }

    public int getProductCount() {
        String substring = mNumber.getText().toString();
        int count = Integer.parseInt(substring);
        return count;
    }

    public void setProductPrice(int price) {
        if (price != -1 && mTv_price != null) {
            mTv_price.setText("$" + price);
        }
    }

    public void setProductName(String name) {
        if (name != null && mTv_title != null) {
            mTv_title.setText(name);
        }
    }

    public void setProductImage(String imageUrl) {
        if (imageUrl != null && mIv_title != null) {
            Glide.with(mContext.getApplicationContext()).load(Urls.BASE_URL + imageUrl).into(mIv_title);
        }
    }

    public static void dismissScanNumberDialog() {
        dialog.dismiss();
    }

    /**
     * 计算选择商品的总价
     *
     * @param number   商品数量
     * @param tv_price 商品价格
     */
    private void computeTotalPrice(final EditText number, final TextView tv_price) {
        number.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //一个的时候的价格

                int price = mData.getPrice();

                //选购的数量productNum
                String s = number.getText().toString();
                int productNum = Integer.parseInt(s);

                tv_price.setText("$" + (price * productNum));
            }
        });
    }


}
