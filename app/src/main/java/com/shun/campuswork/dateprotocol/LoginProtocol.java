package com.shun.campuswork.dateprotocol;

import com.google.gson.Gson;
import com.shun.campuswork.domain.UserInfo;
import com.shun.campuswork.global.GlobalContants;

/**
 * Created by shun99 on 2016/2/14.
 */
public class LoginProtocol extends BaseProtocolNoCache<UserInfo> {
    private String mUser, mPwd;

    public LoginProtocol(String user, String pwd) {
        mUser = user;
        mPwd = pwd;
    }

    @Override
    protected String getUrl() {
        return GlobalContants.SERVER_URL + mUser + "_" + mPwd + ".json";
    }

    @Override
    public UserInfo praseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, UserInfo.class);
    }
}
