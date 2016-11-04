package com.ovu.ibeacon.utils;

public class Utils {
	public final static String URL = "http://120.55.95.87/api//v1/application/data";
	public final static String PARAM6C = "mac=404C49680600026C&token=123456789&appeui=0102030405060708";
	public final static String PARAM6D = "mac=404C49680600026D&token=123456789&appeui=0102030405060708";
	public final static String PARAM6E = "mac=404C49680600026E&token=123456789&appeui=0102030405060708";
	public final static String PARAM6F = "mac=404C49680600026F&token=123456789&appeui=0102030405060708";
	public final static String PARAM70 = "mac=404C496806000270&token=123456789&appeui=0102030405060708";
	public final static String PARAM71 = "mac=404C496806000271&token=123456789&appeui=0102030405060708";
	public final static String PARAM72 = "mac=404C496806000272&token=123456789&appeui=0102030405060708";
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
