package com.song.honestshoppingmall.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.event.FirstEvent;
import com.song.honestshoppingmall.fragment.AddressFragment;
import com.song.honestshoppingmall.fragment.CategoryFragment;
import com.song.honestshoppingmall.fragment.HomeFragment;
import com.song.honestshoppingmall.fragment.MineFragment;
import com.song.honestshoppingmall.fragment.MyOrderFragment;
import com.song.honestshoppingmall.fragment.SerchFragment;
import com.song.honestshoppingmall.fragment.SettingFragment;
import com.song.honestshoppingmall.fragment.ShopCartFragment;
import com.song.honestshoppingmall.util.Constants;
import com.song.honestshoppingmall.util.SpUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {


    @Bind(R.id.fl_home)
    FrameLayout mFlHome;
    @Bind(R.id.rb_home)
    RadioButton mRbHome;
    @Bind(R.id.rb_category)
    RadioButton mRbCategory;
    @Bind(R.id.rb_shopcart)
    RadioButton mRbShopcart;
    @Bind(R.id.rb_mine)
    RadioButton mRbMine;
    @Bind(R.id.rb_setting)
    RadioButton mRbSetting;
    @Bind(R.id.radio_group)
    RadioGroup mRadioGroup;
    private FragmentManager mFragmentManager;
    private DrawerLayout mDrawer_main;
    private NavigationView mNav_view;
    public Toolbar mToolbar_main;
    public TextView mTv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mDrawer_main = (DrawerLayout) findViewById(R.id.drawer_main);
        mNav_view = (NavigationView) findViewById(R.id.nav_view);
        mToolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
        mTv_title = (TextView) findViewById(R.id.tv_title);
        initView();

        //获取FragmentManager
        mFragmentManager = getSupportFragmentManager();
        initData();

    }

    private void initView() {
        setSupportActionBar(mToolbar_main);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.out);
        }
        actionBar.setTitle("");
        mTv_title.setText("老实商城");
        mRadioGroup.setOnCheckedChangeListener(this);

    }

    private void initData() {
        mFragmentManager.beginTransaction().replace(R.id.fl_home, new HomeFragment()).commit();
        mRadioGroup.check(R.id.rb_home);

        mNav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_order:
                        changeFragment(new MyOrderFragment(), "MyOrderFragment");
                        mDrawer_main.closeDrawers();
                        break;
                    case R.id.nav_cart:
                        changeFragment(new ShopCartFragment(), "ShopCartFragment");
                        mDrawer_main.closeDrawers();
                        break;
                    case R.id.nav_ticket:
                        //代金券
                        mDrawer_main.closeDrawers();
                        break;
                    case R.id.nav_address:
                        changeFragment(new AddressFragment(), "AddressFragment");
                        mDrawer_main.closeDrawers();
                        break;
                    case R.id.nav_record:
                        changeFragment(new SettingFragment(), "SettingFragment");
                        mDrawer_main.closeDrawers();
                        break;
                    case R.id.nav_like:
                        mDrawer_main.closeDrawers();
                        //收藏
                        break;
                    case R.id.nav_share:
                        mDrawer_main.closeDrawers();
                        //分享
                        break;


                }


                return true;
            }
        });
    }

    /**
     * 根据RadioGroup的选中子条目，切换Fragment页面
     *
     * @param radioGroup
     * @param i
     */
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        //点击之前先清空栈里面的Fragment
        removeAllFragment();
        switch (i) {
            case R.id.rb_home:
                EventBus.getDefault().post(
                        new FirstEvent("FirstEvent btn clicked rb_home"));
                changeFragment(new HomeFragment(), "HomeFragment");
                break;
            case R.id.rb_category:
                EventBus.getDefault().post(
                        new FirstEvent("FirstEvent btn clicked rb_category"));
                changeFragment(new CategoryFragment(), "CategoryFragment");
                break;
            case R.id.rb_shopcart:
                EventBus.getDefault().post(
                        new FirstEvent("FirstEvent btn clicked rb_shopcart"));
                changeFragment(new ShopCartFragment(), "ShopCartFragment");
                break;
            case R.id.rb_mine:
                EventBus.getDefault().post(
                        new FirstEvent("FirstEvent btn clicked rb_mine"));
                changeFragment(new MineFragment(), "MineFragment");
                break;
            case R.id.rb_setting:
                EventBus.getDefault().post(
                        new FirstEvent("FirstEvent btn clicked rb_setting"));
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
                SpUtil.saveBoolean(this, Constants.LOGIN_STATE, false);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(mFragmentManager.getBackStackEntryCount() > 1)
                    onBackPressed();
                break;
/*            case R.id.cart:
                removeAllFragment();
                changeFragment(new ShopCartFragment(), "ShopCartFragment");
                break;*/
            case R.id.explore:
                changeFragment(new SerchFragment(), "SerchFragment");
                break;
            case R.id.mine:
                mDrawer_main.openDrawer(GravityCompat.START);
                break;

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    public void changeTitle(String title) {
        mTv_title.setText(title);
    }




}
