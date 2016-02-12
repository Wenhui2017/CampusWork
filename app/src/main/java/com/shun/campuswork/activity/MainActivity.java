package com.shun.campuswork.activity;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.shun.campuswork.R;
import com.shun.campuswork.adapter.MyViewPagerAdapter;
import com.shun.campuswork.domain.UserInfo;
import com.shun.campuswork.fragment.HomeFragment;
import com.shun.campuswork.fragment.NewsFragment;
import com.shun.campuswork.fragment.PersonFragment;
import com.shun.campuswork.fragment.ToolFragment;
import com.shun.campuswork.netdate.UserDate;
import com.shun.campuswork.tools.MyBitmapUtils;
import com.shun.campuswork.tools.SharedPreferencesUtils;
import com.shun.campuswork.tools.ToastUtils;
import com.shun.campuswork.tools.UiUtils;
import com.shun.campuswork.view.NoScrollViewPager;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends BaseActivity implements AMapLocationListener,
        NavigationView.OnNavigationItemSelectedListener {
    public Toolbar mToolbar;
    public NoScrollViewPager vp_main_content;
    public static MainActivity mainActivity = null;

    private TextView nav_tv_user;
    private ImageView nav_iv_head;

    @Override
    public void init() {
        if (mainActivity == null) {
            mainActivity = this;
        }
        setContentView(R.layout.activity_main);
        //String d1 = SharedPreferencesUtils.getString("d1");
        initView();
        initViewPager();
        //initUser();
    }

    private void initView() {
        //初始化Toolbar,根据initContentView中初始化的ContentView.
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //右下角的悬浮按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //初始化抽屉
        initNavigationView();
    }

    /**
     * 初始化抽屉
     */
    private void initNavigationView() {
        /*
         * 1）显示Navigation Drawer的 Activity 对象 2） DrawerLayout 对象
		 * 3）一个用来指示Navigation Drawer的 drawable资源 4）一个用来描述打开Navigation Drawer的文本
		 * (用于支持可访问性)。 5）一个用来描述关闭Navigation Drawer的文本(用于支持可访问性).
		 */
        //抽屉
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        // 让开关和actionbar建立关系
        toggle.syncState();
        //为抽屉添加头（等同于在xml中 app:headerLayout="@layout/nav_header_main"）
        View headerView = UiUtils.inflate(R.layout.nav_header_main);
        nav_iv_head = (ImageView) headerView.findViewById(R.id.nav_iv_head);
        nav_tv_user = (TextView) headerView.findViewById(R.id.nav_tv_user);
        nav_iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterUser();
            }
        });
        //初始化登入界面
        initEnter();
        //menu
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.addHeaderView(headerView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * 应从服务器获取。
     * 1..用户--密码
     * 2..用户--用户信息
     */
    private void initUser() {
        String currentUser = "13243189659";
        SharedPreferencesUtils.putString(SharedPreferencesUtils.CURRENT_USER, currentUser);//模拟已经登入用户
        //1..用户--密码
        String pwd = UserDate.getPwd(currentUser);
        Log.w("pwd", "" + pwd);
        SharedPreferencesUtils.putString(currentUser, pwd);
    }

    /**
     * 初始化登入
     */
    private void initEnter() {
        String currentUser = SharedPreferencesUtils.getString(SharedPreferencesUtils.CURRENT_USER);
        Log.w("currentUser", "currentUser" + currentUser);
        if (TextUtils.isEmpty(currentUser)) {
            nav_iv_head.setImageResource(R.mipmap.ic_launcher);
            nav_tv_user.setText("未登入");
        } else {
            String json = SharedPreferencesUtils.getString(currentUser + SharedPreferencesUtils.INFO);
            Log.w("json", "json" + json);
            if (!TextUtils.isEmpty(json)) {
                Gson gson = new Gson();
                UserInfo userInfo = gson.fromJson(json, UserInfo.class);
                MyBitmapUtils.display(nav_iv_head, userInfo.headUrl);
                nav_tv_user.setText(userInfo.user);
            }
        }
    }

    private void EnterUser() {
        String currentUser = SharedPreferencesUtils.getString(SharedPreferencesUtils.CURRENT_USER);
        if (TextUtils.isEmpty(currentUser)) {
            startActivity(new Intent(MainActivity.this, EnterActivity.class));
        } else {
            ToastUtils.makeText("已登入");
        }
    }

    private void initViewPager() {
        vp_main_content = (NoScrollViewPager) findViewById(R.id.vp_main_content);
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(HomeFragment.getInstance());
        viewPagerAdapter.addFragment(NewsFragment.getInstance());
        viewPagerAdapter.addFragment(ToolFragment.getInstance());
        viewPagerAdapter.addFragment(PersonFragment.getInstance());
        vp_main_content.setAdapter(viewPagerAdapter);
    }

    /*处理ToolBar的点击事件*/
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
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            vp_main_content.setCurrentItem(0);
        } else if (id == R.id.nav_news) {
            vp_main_content.setCurrentItem(1);
        } else if (id == R.id.nav_tool) {
            vp_main_content.setCurrentItem(2);
        } else if (id == R.id.nav_person) {
            vp_main_content.setCurrentItem(3);
        } else if (id == R.id.nav_share) {
            //showShare();
        } else if (id == R.id.nav_logout) {
            SharedPreferencesUtils.putString(SharedPreferencesUtils.CURRENT_USER, "");
            initEnter();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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

    /**
     * 处理返回事件
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*定位服务*/
    public AMapLocationClient mlocationClient;//声明AMapLocationClient类对象
    public AMapLocationClientOption mLocationOption;//声明定位参数设置，通过这个类可以对定位的相关参数进行设置

    public void startPosition() {
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
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
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            // 启动定位
            mlocationClient.startLocation();
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

    @Override
    protected void onStart() {
        super.onStart();
        initEnter();
    }
}
