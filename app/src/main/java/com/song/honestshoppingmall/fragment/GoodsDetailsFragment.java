package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.ImageView;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;

/**
 * @Creator Administrator
 * @CreatedDate 2017/1/10 20:07
 * @Description ${TODO}
 * @Updater $Author$
 * @UpdateTime $Date$
 * @UpdateDesc ${TODO}
 */

public class GoodsDetailsFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_goods, null);

        ImageView iv_fragment_goods_back = (ImageView) view.findViewById(R.id.iv_fragment_goods_back);
        iv_fragment_goods_back.setOnClickListener(this);

        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_fragment_goods_back:
                ((HomeActivity) mContext).popBackStack();
                break;
        }
    }
}
