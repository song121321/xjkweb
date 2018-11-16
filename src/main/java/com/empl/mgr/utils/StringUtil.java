package com.empl.mgr.utils;

public class StringUtil {

	public static int string2Int(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static boolean empty(String str) {
		return (null == str || ("").equals(str));
	}
}
