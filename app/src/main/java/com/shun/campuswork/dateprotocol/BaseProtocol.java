package com.shun.campuswork.dateprotocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringWriter;

import android.text.TextUtils;
import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.IOUtils;
import com.shun.campuswork.global.GlobalContants;
import com.shun.campuswork.tools.FileUtils;

/**
 * 加载数据有关主页数据的协议,包含判断从本地，网络读取，保存到本地
 * 
 * @author shun99
 * 
 * @param <T>需要解析的bean对象
 */
public abstract class BaseProtocol<T> {
	private int mIndex;

	/**
	 * 注意不能在此出使用mListener.onRefresh(t);，因为mListener还没有初始化，
	 * mListener的初始化工作在setOnDateListener里进行的。
	 * 
	 * @param index
	 */
	public BaseProtocol(int index) {
		this.mIndex = index;
	}

	/**
	 * 解析数据
	 */
	public abstract T praseJson(String json);

	/**
	 * 获得项目名
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * 从本地获取数据
	 * 
	 * @param index
	 * @return
	 */
	private String loadLocalDate(int index) {
		File dir = FileUtils.getCacheDir();// 获取保存缓存的文件夹
		File file = new File(dir, getName() + "_" + index + getParams());
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					file));
			String firstline = bufferedReader.readLine();// 读取第一行数据
			System.out.println("过期时间" + firstline);
			long outTime = Long.parseLong(firstline);// 转化可比较大小的long
			if (outTime < System.currentTimeMillis()) {// 过期
				System.out.println("已经过期了该数据");
				return null;
			} else {
				String temp;
				StringWriter sw = new StringWriter();
				while ((temp = bufferedReader.readLine()) != null) {
					sw.write(temp);
				}
				return sw.toString();
			}
		} catch (Exception e) {
			return null;
		}
	}

	public String getParams() {
		return ".json";
	}

	/**
	 * 将数据保存到本地
	 * 
	 * @param json
	 * @param index
	 */
	private void savaLocal(String json, int index) {
		BufferedWriter bw = null;
		try {
			File dir = FileUtils.getCacheDir();
			// 在第一行写一个过期时间
			File file = new File(dir, getName() + "_" + index + getParams()); // /mnt/sdcard/googlePlay/cache/home_0
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(System.currentTimeMillis() + 1000 * 60 + "");
			bw.newLine();// 换行
			bw.write(json);// 把整个json文件保存起来
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bw);
		}
	}

	/**
	 * 从服务器获取数据
	 * 
	 * @param index
	 */
	private void loadServerDate(final int index) {
		HttpUtils httpUtils = new HttpUtils();
		String url = GlobalContants.SERVER_URL + getName() + "_" + index
				+ getParams();
		Log.w("url",""+url);
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String json = responseInfo.result;
				Log.w("json", json);
				mListener.onRefresh(praseJson(json));
				savaLocal(json, index);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				error.printStackTrace();
				System.out.println("网络请求失败....");
				mListener.onRefresh(null);
			}
		});
	}

	/**
	 * 监听数据解析
	 */
	public OnDateListener<T> mListener;

	public void setOnDateListener(OnDateListener<T> listener) {
		mListener = listener;
		String json = loadLocalDate(mIndex);
		if (TextUtils.isEmpty(json)) {// 为空，加载服务器数据
			Log.w("tag", "网络数据");
			loadServerDate(mIndex);
		} else {// 不空，加载本地数据
			final T t = praseJson(json);
			if (mListener == null) {
				System.out.println("mListener是空的");
			} else {
				mListener.onRefresh(t);
				Log.w("tag", "缓存数据");
			}
		}
	}

	public interface OnDateListener<T> {
		/**
		 * 当获取到数据时刷新页面，集体实现在子类中
		 */
		public void onRefresh(T t);
	}

}
