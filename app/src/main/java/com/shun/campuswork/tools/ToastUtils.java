package com.shun.campuswork.tools;

import android.widget.Toast;

public class ToastUtils {
	public static void makeText(String text) {
		Toast.makeText(UiUtils.getContext(), text, Toast.LENGTH_SHORT).show();
	}
}
