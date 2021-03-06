package com.ovu.ibeacon.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.ovu.ibeacon.model.IBeaconModel;
import com.ovu.ibeacon.utils.HttpRequest;
import com.ovu.ibeacon.utils.Utils;

/**
 * @author zz 2016/10/31
 */
public class HttpRequestDao2 {

	//节点超时标志
	private boolean timeOutFlag = false;
	//定义从服务器获取的IBeaconList
	private List<IBeaconModel> iBeaconList = new ArrayList<IBeaconModel>();
	//定义在报警范围内的IBeaconList
	private List<IBeaconModel> transformList = new ArrayList<IBeaconModel>();
	//每次从服务器获取数据，得到此时的系统时间
	private int sysTime;

	public interface DataOutOfRangeListener {
		public void getClose(List<IBeaconModel> ib);

		public void getFar();

		public void timeOut(boolean flag, String lastUpdateTime);
	}

	private DataOutOfRangeListener outOfRangeListener;

	public void setDataOutOfRangeListener(
			DataOutOfRangeListener outOfRangeListener) {
		this.outOfRangeListener = outOfRangeListener;
	}

	public HttpRequestDao2(final String url, final String param) {
		// 设置时间线程，向服务器访问数据
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				String data = null;
				HttpRequest hr = new HttpRequest();
				String s = hr.sendGet(url, param);
				System.out.println("--------------开始打印一组数据-----------------");
				System.out.println(s);
				try {
					JSONObject jsonObj = JSONObject.fromObject(s);
					// 获取系统当前时间
					Calendar c = Calendar.getInstance();
					int day=c.get(Calendar.DATE);
					int hour = c.get(Calendar.HOUR_OF_DAY);
					int minute = c.get(Calendar.MINUTE);
					int seconds = c.get(Calendar.SECOND);
					// 取得data数据
					data = jsonObj.getString("data");
					// 取得上次更新数据时间
					String last_update_time = jsonObj
							.getString("last_update_time");
					//计算取得的系统时间，单位s
					sysTime = hour * 60 * 60 + minute * 60 + seconds;
					//计算读取的节点数据时间，单位s
					int readTime = Integer.parseInt(last_update_time.substring(8, 10)) * 60 * 60 + Integer.parseInt(last_update_time.substring(10, 12)) * 60 + Integer.parseInt(last_update_time.substring(12, 14));
					if (timeOutFlag == false) {
						//时间差大于17s, 判断为超时
						if (day > Integer.parseInt(last_update_time.substring(6, 8)) ||(sysTime - readTime) >= 17) {
							timeOutFlag = true;
							System.out.println(jsonObj.getString("mac") + "超时");
							outOfRangeListener.timeOut(timeOutFlag, last_update_time);
						}
					} else {
						//时间差小于17s并且在同一天, 未超时，正常显示
						if (sysTime - readTime < 17 && day == Integer.parseInt(last_update_time.substring(6, 8))) {
							timeOutFlag = false;
							outOfRangeListener.timeOut(timeOutFlag, last_update_time);
						}
					}
					if(data.length()==0){
						if (outOfRangeListener != null)
							outOfRangeListener.getFar();
					} else if (!data.substring(0, 2).equals("01")) { // 判断节点是否已经入网，正确传输数据
						System.out.println("节点还没入网");
					} else if (!timeOutFlag && data.length() != 0) {
						// 得到除去数据头的有效数据字符串dataWithoutHead
						String dataWithoutHead = data.substring(6);
						for (int i = 0; i < dataWithoutHead.length(); i = i + 6) {
							
							// 取出一组beacon数据
							String tempData = dataWithoutHead.substring(i, i + 6);
							IBeaconModel beacon = new IBeaconModel();
							beacon.setUuid(tempData.substring(0, 4));
							// distance是一个2字节16进制数，如11表示1.7m
							String distance = tempData.substring(4);
							beacon.setDistance((double) Integer.parseInt(
									distance, 16) / 10);
							beacon.setUpdateTime(readTime);
							iBeaconList.add(beacon);
							
						}
						//打印iBeaconList中的信息
						printList(iBeaconList);
						//过滤3m之外和不在uuid数组内的数据
						for (IBeaconModel iBeaconModel : iBeaconList) {
							
							if (iBeaconModel.getDistance() < 1 && Utils.hasThisUUID(iBeaconModel.getUuid())) {
								transformList.add(iBeaconModel);
							}
							
						}
						if (transformList != null && transformList.size() != 0) {
							if (outOfRangeListener != null)
								outOfRangeListener.getClose(transformList);
						} else {
							if (outOfRangeListener != null)
								outOfRangeListener.getFar();
						}
						iBeaconList.clear();
						transformList.clear();
						System.out
								.println("--------------打印此组数据结束-----------------");
						System.out.println();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		// 1s执行一次
		timer.schedule(task, 0, 1000);
	}
	
	/**
	 * 节点过滤器，把15s都不传数据的beacon过滤掉
	 * @param list
	 */
	public void modelFilter(List<IBeaconModel> list){
		if(list != null && list.size() != 0){
			for(int i = 0; i < list.size()-1; i++){
				//当此时系统时间和数据的更新时间查大于30s，则将此数据删掉
				if(sysTime - list.get(i).getUpdateTime() > 15){
					list.remove(i);
				}
			}
		}
	}
	
	/**
	 * 打印list中的信息
	 * @param list
	 */
	public void printList(List<IBeaconModel> list){
		if(list!=null && list.size()!=0){
			for (IBeaconModel iBeaconModel : list) {
				System.out.println("iBeaconList UUID=" + iBeaconModel.getUuid()
						+ " RSSI=" + iBeaconModel.getRssi()
						+ " Distance="
						+ iBeaconModel.getDistance() + " updateTime=" + iBeaconModel.getUpdateTime());
			}
		}
	}

	/**
	 * 计算距离函数 rrsi的范围在 21-53 和 55~77 之间有效
	 * 
	 * @param rrsi
	 * @return
	 */
	public double calDistance(int rrsi) {
		double distance = 0;
		int A = rrsi - 54;
		double n = 0;
		if (A <= 4) {
			n = 1.3;
		} else if (A > 4 && A <= 7) {
			n = 1.5;
		} else if (A > 7 && A <= 12) {
			n = 1.75;
		} else if (A > 12 && A <= 15) {
			n = 1.9;
		} else if (A > 15 && A <= 18) {
			n = 2.3;
		} else if (A > 18 && A <= 23) {
			n = 2.5;
		} else {
			n = 2.7;
		}
		distance = Math.pow(10, (double) A / (10 * n));
		return distance;
	}

	/**
	 * 将从服务器得到的数据格式编程可解析的Json字符串，去掉前后的""和中间的各种\
	 * 
	 * @param s
	 * @return
	 */
	public String formatString(String s) {
		return s.substring(1, s.length() - 1).replace("\\", "");
	}

}
