package com.wwk.blog.support.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ArithUtils {

	private static final int SCALE = 2;

	/**
	 * 比较两个double数的大小
	 * @param v1
	 * @param v2
	 * @return true：v1　>=　v2 ; false: v1 < v2
	 */
	public static boolean compare(double v1, double v2) {
		BigDecimal value1 = new BigDecimal(v1);
		BigDecimal value2 = new BigDecimal(v2);

		if (value1.compareTo(value2) >= 0) {
			return true;
		} else {
			return false;
		}
	}
  
	/**
	 * 提供精确的加法运算
	 * @param 待计算数组
	 * @return 两个数的和
	 */
	public static double add(double... values) {
		BigDecimal result = new BigDecimal(0);
		for(double value : values) {
			result = result.add(new BigDecimal(value));
		}
		return result.doubleValue();
	}
	
	
	/**
	 * 提供精确的加法运算
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal value1 = new BigDecimal(Double.toString(v1));
		BigDecimal value2 = new BigDecimal(Double.toString(v2));
		return value1.add(value2).doubleValue();
	}
	
	
	/**
	 * 提供精确的减法运算
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal value1 = new BigDecimal(Double.toString(v1));
		BigDecimal value2 = new BigDecimal(Double.toString(v2));
		return value1.subtract(value2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算,默认保留两位小数点
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个数的积
	 */
	public static double mul(double v1, double v2) {
		return mul(v1, v2, SCALE);
	}

	/**
	 * 提供精确的乘法运算
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @param scale 保留的小数位数
	 * @return 两个数的积
	 */
	public static double mul(double v1, double v2, int scale) {
		BigDecimal value1 = new BigDecimal(Double.toString(v1));
		BigDecimal value2 = new BigDecimal(Double.toString(v2));

		return divide(value1.multiply(value2).doubleValue(), 1, scale);
	}

	/**
	 * 除法运算
	 * @param v1 被除数
	 * @param v2 除数
	 * @return
	 */
	public static double divide(double v1, double v2) {
		return divide(v1, v2, SCALE);
	}

	/**
	 * 除法运算
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 保留的小数位数
	 * @return
	 */
	public static double divide(double v1, double v2, int scale) {
		BigDecimal value1 = new BigDecimal(Double.toString(v1));
		BigDecimal value2 = new BigDecimal(Double.toString(v2));
		 
		return value1.divide(value2, scale, BigDecimal.ROUND_DOWN).doubleValue();
	}
	
	/**
	 * 格式化金额(保留两位小数)
	 * @param number
	 * @return
	 */
	public static String format(Double number) {
		return format(number, "#0.00");
	}
	
	public static String format(Double number, String pattern) {
		if (number == null) {
			number = 0D;
		}
		DecimalFormat format = new DecimalFormat(pattern);
		return format.format(number);
	}

}
