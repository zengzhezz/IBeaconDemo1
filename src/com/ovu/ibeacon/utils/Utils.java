package com.ovu.ibeacon.utils;

public class Utils {
	public final static String URL = "http://120.55.95.87/api//v1/application/data";
	public final static String PARAM6C = "mac=404C49680600026C&token=123456789&appeui=0102030405060708";
	public final static String PARAM6D = "mac=404C49680600026D&token=123456789&appeui=0102030405060708";
	public final static String PARAM6E = "mac=404C49680600026E&token=123456789&appeui=0102030405060708";
	public final static String PARAM6F = "mac=404C49680600026F&token=123456789&appeui=0102030405060708";
	public final static String PARAM70 = "mac=404C496806000270&token=123456789&appeui=0102030405060708";
	public final static String PARAM71 = "mac=404C496806000271&token=123456789&appeui=0102030405060708";
	public final static String PARAM74 = "mac=404C496806000274&token=123456789&appeui=0102030405060708";
	
	public final static int SN_WIDTH = 160;
	
	public final static int FONT_SIZE = 14;
	
	//Ibeacon名称数组
	public final static String[] Beacon_UUID= {
		"000b"
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
