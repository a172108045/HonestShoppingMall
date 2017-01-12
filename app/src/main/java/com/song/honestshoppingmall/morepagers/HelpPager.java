package com.song.honestshoppingmall.morepagers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.HelpBean;
import com.song.honestshoppingmall.bean.HelpDetailBean;
import com.song.honestshoppingmall.util.RetrofitUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Judy on 2017/1/11.
 */

public class HelpPager extends BasePager {
    private int prePosition;

    public HelpPager(Context context) {
        super(context);
    }

    @Override
    public View getRootView() {
        View view = View.inflate(mContext, R.layout.pager_help, null);
        showData((ListView)view);
        return view;
    }

    private void showData(final ListView rootView) {

        RetrofitUtil.getAPIRetrofitInstance().getHelpBean().enqueue(new Callback<HelpBean>() {
            @Override
            public void onResponse(Call<HelpBean> call, Response<HelpBean> response) {
                if (response.isSuccessful()) {

                    final List<HelpBean.HelpListBean> helpList = response.body().getHelpList();
                    if (helpList != null) {
                        rootView.setAdapter(new BaseAdapter() {
                            @Override
                            public int getCount() {
                                return helpList.size();
                            }

                            @Override
                            public HelpBean.HelpListBean getItem(int position) {
                                return helpList.get(position);
                            }

                            @Override
                            public long getItemId(int position) {
                                return position;
                            }

                            @Override
                            public View getView(final int position, View convertView, final ViewGroup parent) {
                                View view = View.inflate(mContext, R.layout.view_help_item, null);

                                TextView tv_title = (TextView) view.findViewById(R.id.tv_help_title);
                                final LinearLayout ll_help_detail = (LinearLayout) view.findViewById(R.id.ll_help_detail);

                                tv_title.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (prePosition != position) {
                                            parent.getChildAt(prePosition).findViewById(R.id.ll_help_detail).setVisibility(View.GONE);
                                        }

                                        if (ll_help_detail.getVisibility() == View.GONE) {
                                            ll_help_detail.setVisibility(View.VISIBLE);
                                        } else {
                                            ll_help_detail.setVisibility(View.GONE);
                                        }
                                        prePosition = position;
                                    }
                                });

                                tv_title.setText(helpList.get(position).getTitle());
                                String id = helpList.get(position).getId() + "";

                                RetrofitUtil.getAPIRetrofitInstance().getHelpDetailBean(id).enqueue(new Callback<HelpDetailBean>() {
                                    @Override
                                    public void onResponse(Call<HelpDetailBean> call, Response<HelpDetailBean> response) {
                                        List<HelpDetailBean.HelpDetailListBean> helpDetailList = response.body().getHelpDetailList();
                                        if (response.isSuccessful() && helpDetailList != null) {
                                            for (int j = 0; j < helpDetailList.size(); j++) {
                                                View view1 = View.inflate(mContext, R.layout.view_help_detail_item, null);
                                                TextView tv1 = (TextView) view1.findViewById(R.id.tv_help_title);
                                                TextView tv2 = (TextView) view1.findViewById(R.id.tv_help_context);
                                                tv1.setText(helpDetailList.get(j).getTitle());
                                                tv2.setText(helpDetailList.get(j).getContent());
                                                ll_help_detail.addView(view1);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<HelpDetailBean> call, Throwable t) {

                                    }
                                });
                                return view;
                            }
                        });


                    } else {
                        Toast.makeText(mContext, "获取服务器数据失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "获取服务器数据失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HelpBean> call, Throwable t) {
                Toast.makeText(mContext, "获取网络失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initData() {



    }
}
