package com.song.honestshoppingmall.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.song.honestshoppingmall.R;

/**
 * Created by Judy on 2017/1/10.
 */

public class WriteBillDialog extends Dialog {

    private String billTitle;
    private String billContent;
    private TextView tvBillHead;
    private final String[] billTitles;
    private final String[] billContents;

    public WriteBillDialog(Context context, String billTitle, String billContent, TextView tvBillHead,String[] billTitles, String[] billContents) {
        super(context, R.style.MyDialog);
        this.billTitle = billTitle;
        this.billContent = billContent;
        this.tvBillHead = tvBillHead;
        this.billTitles = billTitles;
        this.billContents = billContents;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writebill_dialog);
        RadioGroup rg_title = (RadioGroup) findViewById(R.id.rg_title);
        RadioGroup rg_content = (RadioGroup) findViewById(R.id.rg_content);
        TextView tv_confirm = (TextView) findViewById(R.id.tv_confirm);

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteBillDialog.this.dismiss();
            }
        });

        rg_title.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int position = 0;
                switch (checkedId) {
                    case R.id.rb_title_1:
                        position = 0;
                        break;

                    case R.id.rb_title_2:
                        position = 1;
                        break;

                }
                billTitle = billTitles[position];
                tvBillHead.setText(billTitle + "/" + billContent);
            }
        });
        rg_content.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int position = 0;
                switch (checkedId) {
                    case R.id.rb_content_1:
                        position = 0;
                        break;
                    case R.id.rb_content_2:
                        position = 1;
                        break;
                    case R.id.rb_content_3:
                        position = 2;
                        break;
                    case R.id.rb_content_4:
                        position = 3;
                        break;

                }
                billContent = billContents[position];
                tvBillHead.setText(billTitle + "/" + billContent);
            }
        });

    }


}
