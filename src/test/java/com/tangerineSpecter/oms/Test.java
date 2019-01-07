package com.tangerineSpecter.oms;

import java.util.HashMap;
import java.util.Map;

import com.tangerineSpecter.oms.common.utils.HttpUtils;

public class Test {

	public static void main(String[] args) {
		Map<String, Object> data = new HashMap<>();
		data.put("consName", "狮子座");
		data.put("type", "today");
		data.put("key", "47f6796c7b8b99c47c176d56adf4f0a8");
		HttpUtils.interfaceInvoke("http://web.juhe.cn:8080/constellation/getAll", data);
	}
}
