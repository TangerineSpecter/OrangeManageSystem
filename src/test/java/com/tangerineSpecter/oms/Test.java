package com.tangerineSpecter.oms;

public class Test {

	public static void main(String[] args) {
		String[] split = "2019-01-13".split("-");
		for (String s : split) {
			System.out.println(Integer.valueOf(s));
		}
		// String starNameByDate = DateUtils.getStarNameByDate("2011-12-29");
		String a = "post";
		System.out.println(a.toUpperCase().equals("POST"));
	}
}
