package com.song.honestshoppingmall.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.bean.SearchRecommandBean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.Constants;
import com.song.honestshoppingmall.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lizhenquan on 2017/1/10.
 */

public class SerchFragment extends BaseFragment implements View.OnClickListener {

    private Spinner    mSpinner;
    private GridLayout mGridLayout;
    private EditText   mEt_serch;
    private List<String> mData       = new ArrayList<>();
    private List<String> mDataRecent = new ArrayList<>();
    private GridLayout mGrid_search_recent;

    @Override
    protected View initView() {
        ((HomeActivity)mContext).changeTitle("商品搜索");
        View view = View.inflate(mContext, R.layout.fragment_serch, null);
        mSpinner = (Spinner) view.findViewById(R.id.spinner1);
        mGridLayout = (GridLayout) view.findViewById(R.id.grid_search);
        TextView tv_recent = (TextView) view.findViewById(R.id.tv_recent);
        mGrid_search_recent = (GridLayout) view.findViewById(R.id.grid_search_recent);
        Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        mEt_serch = (EditText) view.findViewById(R.id.et_serch);


        initNetData();


        btn_confirm.setOnClickListener(this);
        tv_recent.setOnClickListener(this);

        return view;
    }

    private void gridAddItem() {
        for (int i = 0; i < mData.size(); i++) {
            addItem(mData.get(i));
        }
    }

    private void gridRecentAddItem() {
        for (int i = 0; i < mDataRecent.size(); i++) {
            addRecentItem(mDataRecent.get(i));
        }
    }

    private void initNetData() {

        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        apiRetrofitInstance.getSearchRecommand().enqueue(new Callback<SearchRecommandBean>() {
            @Override
            public void onResponse(Call<SearchRecommandBean> call, Response<SearchRecommandBean> response) {
                if (response.isSuccessful()) {
                    SearchRecommandBean body = response.body();
                    if (body.error == null) {
                        List<String> searchKeywords = body.getSearchKeywords();
                        mData.clear();
                        mData.addAll(searchKeywords);
                        //GridLayout添加子条目
                        gridAddItem();
                      //  Toast.makeText(mContext, "请求成功", Toast.LENGTH_SHORT).show();

                    } else {
                      //  Toast.makeText(mContext, body.error, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchRecommandBean> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addItem(String item) {
        TextView tv = newTextView();
        tv.setText(item);
        mGridLayout.addView(tv);
    }

    public void addRecentItem(String item) {
        TextView tv = newTextView();
        tv.setText(item);
        mGrid_search_recent.addView(tv);
    }


    public TextView newTextView() {
        int margin = 5;
        final TextView tv = new TextView(getContext());

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setMargins(margin, margin, margin, margin);
        params.width = getResources().getDisplayMetrics().widthPixels / 4 - 2 * margin;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(params);
        tv.setBackgroundResource(R.drawable.shape_search_griditem);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = tv.getText().toString();
                mDataRecent.add(0, s);
                Bundle bundle = new Bundle();
                bundle.putString("serchkey", s);
                ((HomeActivity) mContext).changeFragment(new SerchDetailFragment(), "SerchDetailFragment", bundle);
            }
        });
        return tv;
    }

    @Override
    protected void initData() {
        //显示的数组
        final String arr[] = new String[]{
                "宝贝",
                "店铺",
                "活动"

        };
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, arr);
        mSpinner.setAdapter(arrayAdapter);
        //注册事件
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner spinner = (Spinner) parent;
              //  Toast.makeText(mContext.getApplicationContext(), "xxxx" + spinner.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
              //  Toast.makeText(mContext.getApplicationContext(), "没有改变的处理", Toast.LENGTH_LONG).show();
            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();
        gridRecentAddItem();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_confirm:
                //进入搜索详情页面
                String s = mEt_serch.getText().toString();
                mDataRecent.add(0, s);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.SEARCH_KEY, s);
                ((HomeActivity) mContext).changeFragment(new SerchDetailFragment(), "SerchDetailFragment", bundle);
                break;
            case R.id.tv_recent:
                /*Toast.makeText(mContext, "我被点击了", Toast.LENGTH_SHORT).show();
                mDataRecent.clear();
                onResume();*/
                mDataRecent.clear();
                mGrid_search_recent.removeAllViews();
                break;

        }
    }
}
