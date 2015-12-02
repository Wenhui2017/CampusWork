package com.shun.campuswork.global;

/**
 * 存储项目的常量
 * 
 * @author shun99
 * 
 */
public class GlobalContants {
	public static final String SERVER_URL = "http://campuswork1.sinaapp.com/";
	public static final String IMAGE_URL = SERVER_URL + "image?name=";
	public static final String NEWS = "news";

	public static final String WORK_TIME[] = new String[]{"星期天","工作日"};

	public static final String WORK_TYPE[] = new String[]{"派单","礼仪","促销","家教","模特","演出","其他"};
	public static String getWorkType(int pos){
		return WORK_TYPE[pos];
	}

	public static final String SEX[] = new String[]{"不限","男","女"};
	public static String getSex(int pos){
		return SEX[pos];
	}

	public static String getReleaseTime(Long relesaTime){
		return relesaTime +"小时前";
	}

	public static String getSalary(double salary){
		return salary +"元/小时";
	}


}
