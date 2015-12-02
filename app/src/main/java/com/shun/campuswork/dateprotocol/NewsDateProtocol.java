package com.shun.campuswork.dateprotocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shun.campuswork.domain.JobInfo;
import com.shun.campuswork.global.GlobalContants;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by shun99 on 2015/11/26.
 */
public class NewsDateProtocol extends BaseProtocol<List<JobInfo>> {

    /**
     * 注意不能在此出使用mListener.onRefresh(t);，因为mListener还没有初始化，
     * mListener的初始化工作在setOnDateListener里进行的。
     *
     * @param index
     */
    public NewsDateProtocol(int index) {
        super(index);
    }

    @Override
    public List<JobInfo> praseJson(String json) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<JobInfo>>() {
        }.getType();
        List<JobInfo> enums = gson.fromJson(json, collectionType);
        return enums;
    }

    @Override
    public String getName() {
        return GlobalContants.NEWS;
    }
}
