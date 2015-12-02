package com.shun.campuswork.tools;

import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

/**
 * Created by shun99 on 2015/11/4.
 */
public class FileUtils {
	public static final String CACHE = "cache";
	public static final String ICON = "icon";
	public static final String ROOT = "GooglePlay";

	/**
	 * 获取缓存路径的文件夹
	 * 
	 * @return
	 */
	public static File getCacheDir() {
		return getDir(CACHE);
	}

	/**
	 * 获取图片的缓存的文件夹
	 * 
	 * @return
	 */
	public static File getIconDir() {
		return getDir(ICON);
	}

	/**
	 * 获取fileName文件
	 * 
	 * @param fileName
	 * @return
	 */
	private static File getDir(String fileName) {
		StringBuilder path = new StringBuilder();
		if (isSDAvailable()) {
			path.append(Environment.getExternalStorageDirectory()
					.getAbsolutePath());// sd卡根目录
			path.append(File.separator);
			path.append(ROOT);
			path.append(File.separator);
			path.append(fileName);
		} else {
			path.append(UiUtils.getContext().getCacheDir().getAbsolutePath());// data/data/com.shun.googleplay/cache
			path.append(File.separator);
			path.append(fileName);
		}
		File file = new File(path.toString());
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();// 创建文件夹
		}
		return file;
	}

	/**
	 * 判断sd卡是否可用
	 * 
	 * @return
	 */
	private static boolean isSDAvailable() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * 读取流
	 * 
	 * @param inStream
	 * @return 字节数组
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

}
