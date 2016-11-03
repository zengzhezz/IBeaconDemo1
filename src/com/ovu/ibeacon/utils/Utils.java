package com.ovu.ibeacon.utils;

public class Utils {
	public final static String URL = "http://120.55.95.87/api//v1/application/data";
	public final static String PARAM14 = "mac=AABBCC6666442214&token=123456789&appeui=0102030405060708";
	public final static String PARAM15 = "mac=AABBCC6666442215&token=123456789&appeui=0102030405060708";
	public final static String PARAM16 = "mac=AABBCC6666442216&token=123456789&appeui=0102030405060708";
	public final static String PARAM17 = "mac=AABBCC6666442217&token=123456789&appeui=0102030405060708";
	public final static String PARAM18 = "mac=AABBCC6666442218&token=123456789&appeui=0102030405060708";
	public final static String PARAM19 = "mac=AABBCC6666442219&token=123456789&appeui=0102030405060708";
	public final static String PARAM1A = "mac=AABBCC666644221a&token=123456789&appeui=0102030405060708";
//	public final static String PARAM8 = "mac=AABBCC6666442214&token=70B3D5FFFE88B03A&appeui=0102030405060708";
//	public final static String PARAM9 = "mac=AABBCC6666442214&token=70B3D5FFFE88B03A&appeui=0102030405060708";
//	public final static String PARAM10 = "mac=AABBCC6666442214&token=70B3D5FFFE88B03A&appeui=0102030405060708";
	
	//Ibeacon名称数组
	public final static String[] Beacon_UUID= {
		"00000001", "00000002","00000003","00000004","00000005","00000006","00000007","00000008","00000009"
	};
	
	/**
	 * 判断取得的数据是否在设置的UUID中
	 * @param uuid
	 * @return
	 */
	public static boolean hasThisUUID(String uuid){
		for (int i = 0; i < Beacon_UUID.length; i++) {
			if(uuid.equals(Beacon_UUID[i]))
				return true;
		}
		return false;
	}
	
}
