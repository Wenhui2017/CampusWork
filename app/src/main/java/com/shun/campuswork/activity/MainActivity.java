package com.shun.campuswork.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.shun.campuswork.R;
import com.shun.campuswork.dateprotocol.UserDate;
import com.shun.campuswork.domain.UserInfo;
import com.shun.campuswork.fragment.HomeFragment;
import com.shun.campuswork.fragment.NewsFragment;
import com.shun.campuswork.fragment.PersonFragment;
import com.shun.campuswork.fragment.ToolFragment;
import com.shun.campuswork.global.GlobalContants;
import com.shun.campuswork.tools.LogUtils;
import com.shun.campuswork.tools.MyBitmapUtils;
import com.shun.campuswork.tools.ToastUtils;
import com.shun.campuswork.tools.UiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 主页
 */
public class MainActivity extends BaseActivity implements AMapLocationListener,
        NavigationView.OnNavigationItemSelectedListener {
    public static MainActivity instance = null;
    public List<Fragment> mFragmentList;

    private Toolbar mToolbar;
    private Class[] classes = {HomeFragment.class, NewsFragment.class, ToolFragment.class, PersonFragment.class};

    private String mLoggingUser;
    private TextView nav_tv_user;
    private ImageView nav_iv_head;
    private DrawerLayout mDrawer;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (instance == null) {
            instance = this;
        }
        setContentView(R.layout.activity_main);

        mFragmentList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mFragmentList.add(null);
        }
        findViews();
        selectItem(0);
        initLogin();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //初始化抽屉
        initNavigationView();
    }

    private void initNavigationView() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();// 让开关和actionbar建立关系
        //添加headView并设置监听
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = UiUtils.inflate(R.layout.nav_header_main);
        nav_iv_head = (ImageView) headerView.findViewById(R.id.nav_iv_head);
        nav_tv_user = (TextView) headerView.findViewById(R.id.nav_tv_user);
        nav_iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin();
            }
        });
        navigationView.addHeaderView(headerView);
        navigationView.setNavigationItemSelectedListener(this);
        mMenu = navigationView.getMenu();
    }

    public void selectItem(int position) {
        selectItem(position, null);
    }

    public void selectItem(int position, Bundle bundle) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        //先隐藏所有fragment
        for (Fragment fragment : mFragmentList) {
            if (null != fragment) fragmentTransaction.hide(fragment);
        }
        Fragment fragment;
        if (null == mFragmentList.get(position)) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString(GlobalContants.TITLE, GlobalContants.drawerTitles[position]);
            fragment = Fragment.instantiate(this, classes[position].getName(), bundle);
            mFragmentList.set(position, fragment);
            // 如果Fragment为空，则创建一个并添加到界面上
            fragmentTransaction.add(R.id.fl_main_content, fragment);
        } else {
            // 如果Fragment不为空，则直接将它显示出来
            fragment = mFragmentList.get(position);
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
        mToolbar.setTitle(GlobalContants.drawerTitles[position]);
    }

    /**
     * 设置侧边栏被选中的条目
     * @param position 被选中的第几个条目
     * @param state  状态
     */
    public void setNavItemState(int position, Boolean state) {
        mMenu.getItem(position).setChecked(state);
    }

    /**
     * 初始化登入
     */
    private void initLogin() {
        mLoggingUser = UserDate.getLoggingUser();
        if (TextUtils.isEmpty(mLoggingUser)) {
            nav_iv_head.setImageResource(R.mipmap.ic_launcher);
            nav_tv_user.setText("未登入");
        } else {
            String json = UserDate.getUserInfo(mLoggingUser);
            if (!TextUtils.isEmpty(json)) {
                Gson gson = new Gson();
                UserInfo userInfo = gson.fromJson(json, UserInfo.class);
                MyBitmapUtils.display(nav_iv_head, userInfo.headUrl);
                nav_tv_user.setText(userInfo.user);
            }
        }
        if (mFragmentList.size() >= 3 && mFragmentList.get(3) != null) {

            mFragmentList.get(3).onStart();
        }
    }

    private void goLogin() {
        if (TextUtils.isEmpty(mLoggingUser)) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else {
            ToastUtils.makeText("已登入");
        }
    }

    /*处理ToolBar上控件的点击事件*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                return true;
            }
            case R.id.action_position: {
                startPosition();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化ToolBar的点击按钮
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
//        MenuItem menuItem = menu.findItem(R.id.nav_news);
//        menuItem.setChecked(true);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 处理抽屉的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                selectItem(0);
                break;
            case R.id.nav_news:
                selectItem(1);
                break;
            case R.id.nav_tool:
                selectItem(2);
                break;
            case R.id.nav_person:
                selectItem(3);
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_logout:
                UserDate.putLoggingUser("");
                initLogin();
                break;
        }
        item.setChecked(true);
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*分享*/
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initLogin();
    }

    private long lastMillis;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if ((System.currentTimeMillis() - lastMillis) > 2000) {
                ToastUtils.makeText("再点击一次退出程序");
                lastMillis = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }

    /*定位服务*/
    public AMapLocationClient mLocationClient;//声明AMapLocationClient类对象
    public AMapLocationClientOption mLocationOption;//声明定位参数设置，通过这个类可以对定位的相关参数进行设置

    public void startPosition() {
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //设置是否只定位一次,默认为false
            mLocationOption.setOnceLocation(true);
            //设置是否强制刷新WIFI，默认为强制刷新
            mLocationOption.setWifiActiveScan(true);
//            //设置是否允许模拟位置,默认为false，不允许模拟位置
//            mLocationOption.setMockEnable(false);
//            //设置定位间隔,单位毫秒,默认为2000ms
//            mLocationOption.setInterval(2000);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            // 启动定位
            mLocationClient.startLocation();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取经度
                aMapLocation.getLongitude();//获取纬度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getRoad();//街道信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码
                mToolbar.getMenu().getItem(0).setTitle(aMapLocation.getCity());
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                mToolbar.getMenu().getItem(0).setTitle("定位失败");
            }
        }
    }
}
