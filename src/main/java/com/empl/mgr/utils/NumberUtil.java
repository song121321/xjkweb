package com.empl.mgr.utils;

public class NumberUtil {
	public static double roundDouble(double input, double scale) {
		return (double) Math.round(input * Math.pow(10, scale)) / Math.pow(10, scale);
	}

	public static double roundDouble2(double input) {
		return roundDouble(input, 2);
	}
}
