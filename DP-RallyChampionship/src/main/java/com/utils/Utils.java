package com.utils;

/**
 * Common utils
 */
public class Utils {

	/**
	 * Round the number to the first 2 decimal places.
	 * 
	 * @param number The number to round
	 * @return The rounded number
	 */
	public static double round(double number) {
		return Math.round(number * 100.0) / 100.0;
	}
}
