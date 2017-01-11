package com.song.honestshoppingmall.fragment;

import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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

    private ViewPager mVp_fragment_goods_details;
    private TextView mTv_fragment_goods_rawPrice;
    private TextView mTv_fragment_goods_curPrice;
    private RatingBar mRb_fragment_goods_star;
    private TextView mTv_fragment_goods_name;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_goods, null);

        ImageView iv_fragment_goods_back = (ImageView) view.findViewById(R.id.iv_fragment_goods_back);
        iv_fragment_goods_back.setOnClickListener(this);

        //获取商品详情界面需要动态设置属性的子控件
        //商品的翻页viewpager
        mVp_fragment_goods_details = (ViewPager) view.findViewById(R.id.vp_fragment_goods_details);
        //原始价格
        mTv_fragment_goods_rawPrice = (TextView) view.findViewById(R.id.tv_fragment_goods_rawPrice);
        //当前价格
        mTv_fragment_goods_curPrice = (TextView) view.findViewById(R.id.tv_fragment_goods_curPrice);
        //商品评分
        mRb_fragment_goods_star = (RatingBar) view.findViewById(R.id.rb_fragment_goods_star);
        //商品名称
        mTv_fragment_goods_name = (TextView) view.findViewById(R.id.tv_fragment_goods_name);
        //收藏按钮
        Button btn_fragment_goods_collection = (Button) view.findViewById(R.id.btn_fragment_goods_collection);
        //加入购物车按钮
        Button btn_fragment_goods_addCart = (Button) view.findViewById(R.id.btn_fragment_goods_addCart);
        //购买按钮
        Button btn_fragment_goods_buy =  (Button) view.findViewById(R.id.btn_fragment_goods_buy);

        btn_fragment_goods_collection.setOnClickListener(this);
        btn_fragment_goods_addCart.setOnClickListener(this);
        btn_fragment_goods_buy.setOnClickListener(this);

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
            case R.id.btn_fragment_goods_collection:
                //收藏
                break;
            case R.id.btn_fragment_goods_addCart:
                //加入购物车
                break;
            case R.id.btn_fragment_goods_buy:
                //购买
                break;
        }
    }

    //设置商品原价
    public void setRawPrice(int rawPrice){
        if(mTv_fragment_goods_rawPrice != null){
            mTv_fragment_goods_rawPrice.setText("￥" + rawPrice);
            //设置原价view添加中间横线
            mTv_fragment_goods_rawPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    //设置商品现价
    public void setCurPrice(int curPrice){
        if(mTv_fragment_goods_curPrice != null){
            mTv_fragment_goods_curPrice.setText("￥" + curPrice);
        }
    }
}
