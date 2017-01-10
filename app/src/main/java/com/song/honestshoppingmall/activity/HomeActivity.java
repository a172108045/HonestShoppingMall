package com.song.honestshoppingmall.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.fragment.CategoryFragment;
import com.song.honestshoppingmall.fragment.HomeFragment;
import com.song.honestshoppingmall.fragment.MineFragment;
import com.song.honestshoppingmall.fragment.SettingFragment;
import com.song.honestshoppingmall.fragment.ShopCartFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements  RadioGroup.OnCheckedChangeListener {


    @Bind(R.id.fl_home)
    FrameLayout  mFlHome;
    @Bind(R.id.rb_home)
    RadioButton  mRbHome;
    @Bind(R.id.rb_category)
    RadioButton  mRbCategory;
    @Bind(R.id.rb_shopcart)
    RadioButton  mRbShopcart;
    @Bind(R.id.rb_mine)
    RadioButton  mRbMine;
    @Bind(R.id.rb_setting)
    RadioButton  mRbSetting;
    @Bind(R.id.activity_second)
    LinearLayout mActivitySecond;
    @Bind(R.id.radio_group)
    RadioGroup   mRadioGroup;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initView();

        //获取FragmentManager
        mFragmentManager = getSupportFragmentManager();
        initData();

    }

    private void initView() {
        mRadioGroup.setOnCheckedChangeListener(this);

    }

    private void initData() {
        mFragmentManager.beginTransaction().replace(R.id.fl_home, new HomeFragment()).commit();
        mRadioGroup.check(R.id.rb_home);
    }

    /**
     * 根据RadioGroup的选中子条目，切换Fragment页面
     * @param radioGroup
     * @param i
     */
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        //点击之前先清空栈里面的Fragment
        removeAllFragment();
        switch (i) {
            case R.id.rb_home:
                changeFragment(new HomeFragment(), "HomeFragment");
                break;
            case R.id.rb_category:
                changeFragment(new CategoryFragment(), "CategoryFragment");
                break;
            case R.id.rb_shopcart:
                changeFragment(new ShopCartFragment(), "ShopCartFragment");
                break;
            case R.id.rb_mine:
                changeFragment(new MineFragment(), "MineFragment");
                break;
            case R.id.rb_setting:
                changeFragment(new SettingFragment(), "SettingFragment");
                break;
        }
    }
    /**
     * 切换页面， 并且是有返回栈的
     *
     * @param fragment 要修改成哪一个Fragment的实例
     * @param tag      标记
     */
    public void changeFragment(Fragment fragment, String tag) {
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_home, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    /**
     * 切换页面， 可以传递数据
     *
     * @param fragment 要修改成哪一个Fragment的实例
     * @param tag      标记
     * @param bundle   要传递的数据
     */
    public void changeFragment(Fragment fragment, String tag, Bundle bundle) {
        fragment.setArguments(bundle);
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_home, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    ;

    /**
     * 清空回退栈中的所有Fragment
     */
    public void removeAllFragment() {
        getSupportFragmentManager().popBackStack(null, 1);
    }

    /**
     * 模拟退栈，抛出最上层的Fragment
     */
    public void popBackStack() {
        getSupportFragmentManager().popBackStack(null, 0);//参数为0，清除栈顶的Fragment，参数为1，清空栈
    }


    /**
     * 实现两秒内连续点击回退按钮退出效果
     */
    long latestTime;

    @Override
    public void onBackPressed() {
        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        if (backStackEntryCount < 2) { //也就是backStackEntryCount=1，即当前已经在主页了
            if (System.currentTimeMillis() - latestTime < 2000) {
                finish();
                System.exit(0);
            } else {
                Toast.makeText(HomeActivity.this, "再次点击退出", Toast.LENGTH_SHORT).show();
                latestTime = System.currentTimeMillis();
                return;
            }

        }
        super.onBackPressed();

    }




}
