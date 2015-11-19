package com.shun.campuswork.activity;


import android.view.MenuItem;
import com.shun.campuswork.R;

public class MainActivity extends BaseActivity {


    @Override
    public void initContentView() {
        setContentView(R.layout.activity_main);
    }


    /**处理ToolBar的点击事件*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
