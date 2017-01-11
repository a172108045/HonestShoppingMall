package com.song.honestshoppingmall.morepagers;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.song.honestshoppingmall.R;

/**
 * Created by Judy on 2017/1/11.
 */

public class ResponsePager extends BasePager {

    private Button mBt_submit;

    public ResponsePager(Context context) {
        super(context);
    }

    @Override
    public View getRootView() {
        View view = View.inflate(mContext, R.layout.pager_response, null);
        mBt_submit = (Button) view.findViewById(R.id.bt_submit);

        return view;
    }

    @Override
    public void initData() {
        mBt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "提交成功，感谢您的反馈，我们会尽快处理！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
