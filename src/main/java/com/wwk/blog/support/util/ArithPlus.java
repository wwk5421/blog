package com.wwk.blog.support.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * TODO 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算，包括加减乘除和四舍五入。
 * 
 * @author leonse
 * @dateTime May 10, 2014 10:56:14 AM
 * @version 1
 */
public class ArithPlus {
	// 默认除法运算精度
	private static final ComputationalScale DEF_DIV_SCALE = new ComputationalScale(2);
	private static final ComputationalScale DEF_DIV_SCALE_TO_FOUR = new ComputationalScale(4);
	private static final ComputationalScale DEF_DIV_SCALE_TO_EIGHT = new ComputationalScale(8);

	// 这个类不能实例化
	private ArithPlus() {
	}

	/**
	 * 取余模型,用于保留小数位的方式
	 * 
	 * @author leonse
	 *
	 */
	public static class ComputationalScale {
		private final int scale;
		private final int roundingMode;

		/**
		 * @param scale
		 *            保持2位小数,采用默认的 BigDecimal.ROUND_HALF_UP模式
		 * @author leonse
		 */
		public ComputationalScale(int scale) {
			this(scale, BigDecimal.ROUND_HALF_UP);
		}

		/**
		 * @param scale
		 *            保持2位小数,采用默认的
		 * @param roundingMode
		 *            取整模式,详情参考 BigDecimal 里的 roundingMode
		 * @author leonse
		 */
		public ComputationalScale(int scale, int roundingMode) {
			this.scale = scale;
			this.roundingMode = roundingMode;
		}

		public int getScale() {
			return scale;
		}

		public int getRoundingMode() {
			return roundingMode;
		}

	}

	/**
	 * 算术模型
	 * 
	 * @author leonse
	 */
	private static abstract class ComputationalModel {
		protected final double[] vs;
		protected final ComputationalScale computationalScale;

		/**
		 * @param vs
		 *            需要计算的数组
		 * @param computationalScale
		 *            取余模型
		 * @author leonse
		 * 
		 */
		public ComputationalModel(double[] vs,
				ComputationalScale computationalScale) {
			super();
			this.vs = vs;
			this.computationalScale = computationalScale;
		}

		/**
		 * 计算模板.
		 * 
		 * @return 计算结果
		 * 
		 * @author leonse
		 */
		public double computational() {
			BigDecimal bigDecimal = null;
			for (double v : vs) {
				if (bigDecimal == null) {
					bigDecimal = BigDecimal.valueOf(v);
				} else {
					bigDecimal = doComputational(bigDecimal,
							BigDecimal.valueOf(v));
				}
			}
			return bigDecimal.setScale(computationalScale.getScale(),
					computationalScale.getRoundingMode()).doubleValue();
		}

		/**
		 * 真正计算的方法
		 * 
		 * @param v1
		 *            被操作数
		 * @param v2
		 *            操作数
		 * @return
		 * 
		 * @author leonse
		 */
		protected abstract BigDecimal doComputational(BigDecimal v1,
				BigDecimal v2);
	}

	public static void main(String[] args) {
//		System.out.println(add(6, 2, 3));
//		System.out.println(sub(6, 2, 3));
//		System.out.println(mul(371.29, 1.01));
		System.out.println(divToEight(8,1.02));
	}

	// / 加减乘除开始;
	/**
	 * 加法运算
	 * 
	 * @param vs
	 *            加数
	 * @return N个参数的和
	 * 
	 * @author leonse
	 * @time 2015年10月15日 下午4:43:32
	 */
	public static double add(double... vs) {
		ComputationalModel computationalModel = new ComputationalModel(vs,
				DEF_DIV_SCALE) {
			@Override
			protected BigDecimal doComputational(BigDecimal v1, BigDecimal v2) {
				return v1.add(v2);
			}
		};
		return computationalModel.computational();
	}

	/**
	 * 加法运算
	 * 
	 * @param scale
	 *            取余模型
	 * 
	 * @param vs
	 *            加数
	 * @return N个参数的和
	 * 
	 * @author leonse
	 * @time 2015年10月15日 下午4:43:32
	 */

	public static double add(ComputationalScale scale, double... vs) {
		ComputationalModel computationalModel = new ComputationalModel(vs,
				scale) {
			@Override
			protected BigDecimal doComputational(BigDecimal v1, BigDecimal v2) {
				return v1.add(v2);
			}
		};
		return computationalModel.computational();
	}

	/**
	 * TODO 提供精确的减法运算。
	 * 
	 * @param vs
	 *            第一个是被减数,其他的是减数
	 * @return N个参数的差
	 * 
	 * @author leonse
	 */
	public static double sub(double... vs) {
		ComputationalModel computationalModel = new ComputationalModel(vs,
				DEF_DIV_SCALE) {
			@Override
			protected BigDecimal doComputational(BigDecimal v1, BigDecimal v2) {
				return v1.subtract(v2);
			}
		};
		return computationalModel.computational();

	}
	
	/**
	 * TODO 提供精确的减法运算 (精确到4位)。
	 * 
	 * @param vs
	 *            第一个是被减数,其他的是减数
	 * @return N个参数的差
	 * 
	 * @author leonse
	 */
	public static double subCorrectToFourBit(double... vs) {
		ComputationalModel computationalModel = new ComputationalModel(vs,
				DEF_DIV_SCALE_TO_FOUR) {
			@Override
			protected BigDecimal doComputational(BigDecimal v1, BigDecimal v2) {
				return v1.subtract(v2);
			}
		};
		return computationalModel.computational();

	}

	/**
	 * TODO 提供精确的减法运算。
	 * 
	 * @param scale
	 *            取余模型
	 * @param vs
	 *            第一个是被减数,其他的是减数
	 * @return N个参数的差
	 */
	public static double sub(ComputationalScale scale, double... vs) {
		ComputationalModel computationalModel = new ComputationalModel(vs,
				scale) {
			@Override
			protected BigDecimal doComputational(BigDecimal v1, BigDecimal v2) {
				return v1.subtract(v2);
			}
		};
		return computationalModel.computational();

	}

	/**
	 * TODO 提供精确的乘法运算。
	 * 
	 * @param vs
	 *            乘数
	 * @return N个参数的积
	 */
	public static double mul(double... vs) {
		ComputationalModel computationalModel = new ComputationalModel(vs,
				DEF_DIV_SCALE) {
			@Override
			protected BigDecimal doComputational(BigDecimal v1, BigDecimal v2) {
				return v1.multiply(v2);
			}
		};
		return computationalModel.computational();
	}

	/**
	 * TODO 提供精确的乘法运算。
	 * 
	 * @param scale
	 *            取余模型
	 * @param vs
	 *            乘数
	 * @return N个参数的积
	 * 
	 * @author leonse
	 */
	public static double mul(ComputationalScale scale, double... vs) {
		ComputationalModel computationalModel = new ComputationalModel(vs,
				scale) {
			@Override
			protected BigDecimal doComputational(BigDecimal v1, BigDecimal v2) {
				return v1.multiply(v2);
			}
		};
		return computationalModel.computational();
	}

	/**
	 * TODO 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param vs
	 *            除数
	 * @return N个参数的商
	 */
	public static double div(double... v1) {
		return div(DEF_DIV_SCALE, v1);
	}
	
	public static double divToFour(double... v1) {
		return div(DEF_DIV_SCALE_TO_FOUR, v1);
	}
	
	public static double divToEight(double... v1) {
		return div(DEF_DIV_SCALE_TO_EIGHT, v1);
	}

	/**
	 * TODO 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @param vs
	 *            第一个是被除数数,其他的是除数
	 * @return N个参数的商
	 * 
	 * @author leonse
	 * @time 2015年10月15日 下午5:01:39
	 */
	public static double div(ComputationalScale scale, double... vs) {
		ComputationalModel computationalModel = new ComputationalModel(vs,
				scale) {
			@Override
			protected BigDecimal doComputational(BigDecimal v1, BigDecimal v2) {
				return v1.divide(v2, super.computationalScale.getScale(),
						super.computationalScale.getRoundingMode());
			}
		};
		return computationalModel.computational();

	}

	/**
	 * 格式化输出,忽略2位小数之后的数据
	 * 
	 * @param v
	 *            浮点数
	 * @return
	 * 
	 * @author leonse
	 */
	public static String decimalFormat(double v) {
		DecimalFormat d = new DecimalFormat("0.00");
		return d.format(v);
	}

	/**
	 * TODO 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

};
