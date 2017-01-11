package com.song.honestshoppingmall.fragment;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.morepagers.AboutPager;
import com.song.honestshoppingmall.morepagers.BasePager;
import com.song.honestshoppingmall.morepagers.HelpPager;
import com.song.honestshoppingmall.morepagers.RecordPager;
import com.song.honestshoppingmall.morepagers.ResponsePager;

import java.util.ArrayList;
import java.util.List;

import static com.song.honestshoppingmall.R.id.bt_help;

/**
 * Created by Judy on 2017/1/8.
 */

public class SettingFragment extends BaseFragment implements View.OnClickListener {
    private Button mBtHelp;
    private Button mBtRecord;
    private Button mBtResponse;
    private Button mBtAbout;
    private ViewPager mViewPager;
    private List<BasePager> pagers = new ArrayList<>();
    private MoreAdapter mAdapter;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_setting, null);
        mBtHelp = (Button) view.findViewById(bt_help);
        mBtRecord = (Button) view.findViewById(R.id.bt_record);
        mBtResponse = (Button) view.findViewById(R.id.bt_response);
        mBtAbout = (Button) view.findViewById(R.id.bt_about);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);

        return view;
    }

    @Override
    protected void initData() {
        HelpPager helpPager = new HelpPager(mContext);
        RecordPager recordPager = new RecordPager(mContext);
        ResponsePager responsePager = new ResponsePager(mContext);
        AboutPager aboutPager = new AboutPager(mContext);
        pagers.add(helpPager);
        pagers.add(recordPager);
        pagers.add(responsePager);
        pagers.add(aboutPager);
        mAdapter = new MoreAdapter();
        mViewPager.setAdapter(mAdapter);
        mBtHelp.setOnClickListener(this);
        mBtRecord.setOnClickListener(this);
        mBtResponse.setOnClickListener(this);
        mBtAbout.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mBtHelp.performClick();
                        break;
                    case 1:
                        mBtRecord.performClick();
                        break;
                    case 2:
                        mBtResponse.performClick();
                        break;
                    case 3:
                        mBtAbout.performClick();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case bt_help:
                mBtHelp.setEnabled(false);
                mBtRecord.setEnabled(true);
                mBtResponse.setEnabled(true);
                mBtAbout.setEnabled(true);

                mBtHelp.setTextColor(Color.WHITE);
                mBtRecord.setTextColor(Color.BLACK);
                mBtResponse.setTextColor(Color.BLACK);
                mBtAbout.setTextColor(Color.BLACK);

                mViewPager.setCurrentItem(0);

                break;
            case R.id.bt_record:
                mBtHelp.setEnabled(true);
                mBtRecord.setEnabled(false);
                mBtResponse.setEnabled(true);
                mBtAbout.setEnabled(true);

                mBtHelp.setTextColor(Color.BLACK);
                mBtRecord.setTextColor(Color.WHITE);
                mBtResponse.setTextColor(Color.BLACK);
                mBtAbout.setTextColor(Color.BLACK);

                mViewPager.setCurrentItem(1);

                break;
            case R.id.bt_response:
                mBtHelp.setEnabled(true);
                mBtRecord.setEnabled(true);
                mBtResponse.setEnabled(false);
                mBtAbout.setEnabled(true);

                mBtHelp.setTextColor(Color.BLACK);
                mBtRecord.setTextColor(Color.BLACK);
                mBtResponse.setTextColor(Color.WHITE);
                mBtAbout.setTextColor(Color.BLACK);

                mViewPager.setCurrentItem(2);

                break;
            case R.id.bt_about:
                mBtHelp.setEnabled(true);
                mBtRecord.setEnabled(true);
                mBtResponse.setEnabled(true);
                mBtAbout.setEnabled(false);

                mBtHelp.setTextColor(Color.BLACK);
                mBtRecord.setTextColor(Color.BLACK);
                mBtResponse.setTextColor(Color.BLACK);
                mBtAbout.setTextColor(Color.WHITE);

                mViewPager.setCurrentItem(3);

                break;
        }
    }

    class MoreAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = pagers.get(position).getRootView();
            pagers.get(position).initData();
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
