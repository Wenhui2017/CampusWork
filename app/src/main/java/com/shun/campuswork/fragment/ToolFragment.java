package com.shun.campuswork.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shun.campuswork.R;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.tools.UiUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 实用工具的fragment--单例模式
 * Created by shun99 on 2015/11/19.
 */
public class ToolFragment extends Fragment {
    private static ToolFragment instance = null;

    public static ToolFragment getInstance() {
        if (instance == null) {
            instance = new ToolFragment();
        }
        return instance;
    }

    private ToolFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final TextView textView = new TextView(getActivity());
        textView.setText("这是实用工具");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = createDate();
                textView.setText(json);
                Log.w("json..", json + "...");
            }
        });
        return textView;
    }

    private String createDate() {
        final String[] strings = new String[]{"高校快递", "支付宝口碑推广", "app测试", "校外派单", "校园代表", "10080客服"};
        final String[] citys = new String[]{"新华", "卫东", "宝丰"};
        Gson gson = new Gson();
        ArrayList<JobInfo> jsonList = new ArrayList<JobInfo>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            JobInfo jobInfo = new JobInfo();
            jobInfo.city = "平顶山";
            jobInfo.area = citys[random.nextInt(2)];
            jobInfo.isLongWork = (random.nextInt(1) == 0 ? false : true);
            jobInfo.star = random.nextInt(2);
            jobInfo.title = strings[random.nextInt(5)] + i;
            jobInfo.type = random.nextInt(6);
            jobInfo.salary = 11;
            jobInfo.releaseTime = System.currentTimeMillis();

            jobInfo.name = "李经理";
            jobInfo.tel = "13245678963";
            jobInfo.desAddress = "河南省平顶山新华区湖光花园小区";
            jobInfo.sex = 0;
            jobInfo.company= "百胜客";
            jsonList.add(jobInfo);
        }
        return gson.toJson(jsonList);
    }
}
