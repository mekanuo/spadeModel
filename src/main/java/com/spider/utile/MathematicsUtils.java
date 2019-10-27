package com.spider.utile;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MathematicsUtils {
	
	/**
	 * 任务进度计算器
	 * @param num 当前进度
	 * @param total  总任务量
	 * @param scale  精确度
	 * @return
	 */
	public static String accuracy(double num, double total, int scale){
		DecimalFormat df = (DecimalFormat)NumberFormat.getInstance();
		//可以设置精确几位小数
		df.setMaximumFractionDigits(scale);
		//模式 例如四舍五入
		df.setRoundingMode(RoundingMode.HALF_UP);
		double accuracy_num = num / total * 100;
		return df.format(accuracy_num)+"%";
	}

}
