package com.shun.campuswork.dateprotocol;


import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 通过设置监听获取数据
 * <p/>
 * 加载数据有关主页数据的协议,包含判断从本地，网络读取，保存到本地
 *
 * @param <T>需要解析的bean对象,如果解析结果是List集合 T=List<JobInfo>
 * @author shun99
 */
public abstract class BaseProtocolNoCache<T> {

    private String mUrl;

    public OnDateListener<T> mListener;


    /**
     * 获得项目名
     *
     * @return
     */
    protected abstract String getUrl();

    /**
     * 从服务器获取数据
     */
    private void loadServerDate() {
        mUrl = getUrl();
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                mListener.onSuccess(praseJson(responseInfo.result), responseInfo.result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                mListener.onFailure(error, msg);
            }
        });
    }

    /**
     * 解析数据
     */
    public abstract T praseJson(String json);

    //====================================================================获取数据的监听

    /**
     * 通过设置监听获取数据
     *
     * @param listener
     */
    public void setOnDateListener(OnDateListener<T> listener) {
        mListener = listener;
        loadServerDate();
    }

    public interface OnDateListener<T> {
        void onSuccess(T t, String json);

        void onFailure(HttpException error, String msg);
    }

}
