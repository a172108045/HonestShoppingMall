package com.song.honestshoppingmall.util;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.AddCartBean;
import com.song.honestshoppingmall.bean.GoodsBean;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by lizhenquan on 2017/1/7.
 */

public class DialogAlertUtils {
    private static AlertDialog dialog;

    /**
     *
     * @param context
     *            上下文
     * @param productBean

     */
    public static void showScanNumberDialog(final Context context, GoodsBean.ProductBean productBean) {
        //Dialog dialog = new Dialog(context)
        // SysApplication.getInstance().exit();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 创建对话框
        dialog = builder.create();
        // 没有下面这句代码会导致自定义对话框还存在原有的背景

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
        final Button btn_card_remove = (Button) window.findViewById(R.id.btn_card_remove);
        final Button btn_card_add = (Button) window.findViewById(R.id.btn_card_add);
        final Button btn_card_tianjia = (Button) window.findViewById(R.id.btn_card_tianjia);
        final EditText number = (EditText) window.findViewById(R.id.et_number);
        final TextView tv_price = (TextView) window.findViewById(R.id.tv_price);
        RadioButton rb_carddialog_color1 = (RadioButton) window.findViewById(R.id.rb_carddialog_color1);
        rb_carddialog_color1.setChecked(true);
        RadioButton rb_carddialog_size1 = (RadioButton) window.findViewById(R.id.rb_carddialog_size1);
        rb_carddialog_size1.setChecked(true);
        ImageView iv_carddialog_dismiss = (ImageView) window.findViewById(R.id.iv_carddialog_dismiss);
        iv_carddialog_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissScanNumberDialog();
            }
        });
        final String num = number.getText().toString();
        int numInt = Integer.parseInt(num);
        if (numInt<=1){
            btn_card_remove.setEnabled(false);
        }
        btn_card_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_card_add.setEnabled(true);
                String num = number.getText().toString();
                int i = Integer.parseInt(num);
                if (i <=1){
                    btn_card_remove.setEnabled(false);
                }
              number.setText(i-1+"");
            }
        });
        btn_card_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               btn_card_remove.setEnabled(true);
                String num = number.getText().toString();
                int i = Integer.parseInt(num);
               number.setText(i+1+"");
            }
        });
        btn_card_tianjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送get请求
                Map<String,String> map = new HashMap<String, String>();
                map.put("userId",SpUtil.getString(context, Constants.USERID,""));
                map.put("productId","27");
                map.put("productCount","1");
                map.put("propertyId","1");

                APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
                apiRetrofitInstance.getAddCartBean(map).enqueue(new Callback<AddCartBean>() {
                    @Override
                    public void onResponse(Call<AddCartBean> call, Response<AddCartBean> response) {
                        if (response.isSuccessful()) {
                            if (response.body().error==null){
                                Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();

                                dismissScanNumberDialog();
                            }else{
                                Toast.makeText(context, response.body().error, Toast.LENGTH_SHORT).show();
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<AddCartBean> call, Throwable t) {
                        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


                Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                dismissScanNumberDialog();
            }
        });
        final String mOriginPrice = tv_price.getText().toString().substring(1);
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

                int price = Integer.parseInt(mOriginPrice);

                //选购的数量productNum
                String s = number.getText().toString();
                int productNum = Integer.parseInt(s);

                tv_price.setText("$"+(price*productNum));
            }
        });

    }

    public static void dismissScanNumberDialog() {
        dialog.dismiss();
    }

}
