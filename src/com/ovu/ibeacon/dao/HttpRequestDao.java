package com.ovu.ibeacon.dao;

import java.util.Timer;
import java.util.TimerTask;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.ovu.ibeacon.utils.HttpRequest;

public class HttpRequestDao {
	
	private final String url = "http://120.55.95.87/api//v1/application/data";
	private final String param = "mac=AABBCC6666442216&token=70B3D5FFFE88B03A&appeui=0102030405060708";
	
	public interface DataOutOfRangeListener{
		public void getClose();
		public void getFar();
	}
	
	private DataOutOfRangeListener outOfRangeListener;
	
	public void setDataOutOfRangeListener(DataOutOfRangeListener outOfRangeListener){
		this.outOfRangeListener = outOfRangeListener;
	}
	
	public HttpRequestDao() {
		//设置时间线程，向服务器访问数据
		Timer timer = new Timer();
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				String data = null;
				HttpRequest hr = new HttpRequest();
				String s = hr.sendGet(url, param);	
				s = formatString(s);
				System.out.println(s);
				try {
					JSONObject jsonObj = JSONObject.fromObject(s);
					//取得data数据
					data = jsonObj.getString("data");
					System.out.println(data);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if(data.equals("hehe")){
					if(outOfRangeListener != null)
						outOfRangeListener.getClose();
				}else{
					if(outOfRangeListener != null)
						outOfRangeListener.getFar();
				}
			}		
		};
		timer.schedule(task, 0, 1000);
	}
	
	public double calDistance(int rrsi){
		double distance = 0;
		int A = rrsi - 52;
		double n = 0;
		if(A > 1 && A <= 4){
			n = 1.3;
		}else if(A > 4 && A <= 7){
			n = 1.75;
		} else if (A > 7 && A <= 12) {
			n = 2.10;
		} else if (A > 12 && A <= 15) {
			n = 2.5;
		} else if (A > 15 && A <= 18) {
			n = 2.75;
		} else if (A > 18 && A <= 23) {
			n = 3.3;
		}else{
			return 100;
		}
		System.out.println(A);
		distance = Math.pow(10, (double)A / (10 * n));
		return distance;
	}
	
	public static void main(String[] args) {
		HttpRequestDao htpDao = new HttpRequestDao();
		double distance = htpDao.calDistance(67);
		System.out.println(distance);
	}
	
	public String formatString(String s){
		return s.substring(1, s.length()-1).replace("\\", "");
	}
	
}
