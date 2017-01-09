package com.song.honestshoppingmall.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.song.honestshoppingmall.R;


/**
 * Created by lizhenquan on 2017/1/7.
 */

public class DialogAlertUtils {
    private static AlertDialog dialog;

    /**
     *
     * @param context
     *            上下文

     */
    public static void showScanNumberDialog(final Context context) {
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
                Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                dismissScanNumberDialog();
            }
        });

    }

    public static void dismissScanNumberDialog() {
        dialog.dismiss();
    }

}
