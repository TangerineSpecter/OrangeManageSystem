package com.tangerineSpecter.oms;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {

	public static void main(String[] args) {
		String[] split = "2019-01-13".split("-");
		for (String s : split) {
			System.out.println(Integer.valueOf(s));
		}
		try {
			int a = 1 / 0;
		} catch (Exception e) {
			log.error("接口请求异常:{}", e);
		}
	}
}
