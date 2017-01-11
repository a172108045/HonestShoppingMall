package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.util.Constants;
import com.song.honestshoppingmall.util.SpUtil;

/**
 * Created by zan on 2017/1/9.
 */

public class UserFragment extends BaseFragment implements View.OnClickListener{

    private Button bt_logout;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_mine_user, null);
        bt_logout = (Button) view.findViewById(R.id.bt_logout_user);

        initclick();

        return view;
    }

    private void initclick() {
        bt_logout.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_logout_user:
                SpUtil.saveString(mContext, Constants.USERID, null);
                ((HomeActivity) mContext).changeFragment(new MineFragment(), "MineFragment");
                Toast.makeText(mContext, "Good Bye,Honey!", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
