/**
 * DES加密解密
 */
package com.resmanager.client.utils;

import android.util.Log;

public class DESUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println(decrypt("105%116%114%103%72%75%75%75%75%76%75%72%75%75%"));

		System.out.println(encrypt("NYWL-000370-00"));
	}

	/**
	 * 用户名解密
	 * 
	 * @param ssoToken
	 *            字符串
	 * @return String 返回加密字符串
	 */
	public static String decrypt(String ssoToken) {
		try {
			 /* ssoToken:105&116&114&103&72&75&75&80&80&81&76&72&75&75& */
			String name = new String();
			//根据自定义字符为分界符进行拆分,这里的&便是分隔符
			Log.i("-----","hsw+ssoToken:"+ssoToken);
			java.util.StringTokenizer st = new java.util.StringTokenizer(ssoToken, "&");
			while (st.hasMoreElements()) {
				int asc = Integer.parseInt((String) st.nextElement()) - 27;
				//ASCII转码
				name = name + (char) asc;
				Log.i("-----1","hsw+name:"+name);
			}
			Log.i("-----2","hsw+name:"+name);
			return name;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 用户名加密
	 * 
	 * @param ssoToken
	 *            字符串
	 * @return String 返回加密字符串
	 */
	public static String encrypt(String ssoToken) {
		try {
			byte[] _ssoToken = ssoToken.getBytes("ISO-8859-1");
			String name = new String();
			// char[] _ssoToken = ssoToken.toCharArray();
			for (int i = 0; i < _ssoToken.length; i++) {
				int asc = _ssoToken[i];
				_ssoToken[i] = (byte) (asc + 27);
				name = name + (asc + 27) + "&";
			}
			return name;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
