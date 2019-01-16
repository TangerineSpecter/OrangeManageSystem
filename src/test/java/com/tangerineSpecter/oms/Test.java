package com.tangerineSpecter.oms;

import com.tangerineSpecter.oms.common.utils.DateUtils;

public class Test {

	public static void main(String[] args) {
		String[] split = "2019-01-13".split("-");
		for (String s : split) {
			System.out.println(Integer.valueOf(s));
		}
		String starNameByDate = DateUtils.getStarNameByDate("2019-06-28");
		System.out.println(starNameByDate);
	}
}
