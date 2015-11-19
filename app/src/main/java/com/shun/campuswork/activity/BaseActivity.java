package com.shun.campuswork.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shun.campuswork.R;
import com.shun.campuswork.adapter.MyViewPagerAdapter;
import com.shun.campuswork.fragment.HomeFragment;
import com.shun.campuswork.fragment.NewsFragment;
import com.shun.campuswork.fragment.PersonFragment;
import com.shun.campuswork.fragment.ToolFragment;
import com.shun.campuswork.view.NoScrollViewPager;

/**
 * Created by shun99 on 2015/11/19.
 */
public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Toolbar mToolbar;
    public NoScrollViewPager vp_main_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        initToolBar();
        initFloatingActionButton();
        initNavigationView();
        initViewPager();
    }

    /**
     * 初始化contentView，必须实现
     */
    public abstract void initContentView();

    /**
     * 初始化Toolbar
     */
    public void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    /**
     * 初始化悬浮按钮
     */
    private void initFloatingActionButton() {
        //右下角的悬浮按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * 初始化抽屉
     */
    private void initNavigationView() {
        //布局容器
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //抽屉
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        } else if (id == R.id.nav_send) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
