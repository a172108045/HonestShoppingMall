package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.Button;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;

import static com.song.honestshoppingmall.R.id.bt_add_address;

/**
 * Created by zan on 2017/1/11.
 */

public class AddressFragment extends BaseFragment implements View.OnClickListener {

    private Button add_address;

    @Override
    protected View initView() {
        ((HomeActivity)mContext).changeTitle("地址管理");
        View view = View.inflate(mContext, R.layout.fragment_mine_user_address, null);

        add_address = (Button) view.findViewById(bt_add_address);

        initOnClick();
        return view;
    }

    private void initOnClick() {
        add_address.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add_address:
                ((HomeActivity) mContext).changeFragment(new AddAddressFragment(), "AddAddressFragment");
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity)mContext).changeTitle("地址管理");
    }
}
