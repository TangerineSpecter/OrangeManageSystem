package com.tangerineSpecter.oms;

import java.security.NoSuchAlgorithmException;

import com.tangerineSpecter.oms.common.constant.CommonConstant;
import com.tangerineSpecter.oms.common.utils.MD5Utils;

public class Test {

	public static void main(String[] args) {
		try {
			String md5Pwd = MD5Utils.getMD5Pwd("123456", CommonConstant.SALT);
			System.out.println(md5Pwd);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
