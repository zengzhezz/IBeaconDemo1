package com.ovu.ibeacon.dao;

import java.math.BigInteger;
import java.util.ArrayList;
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

	private List<IBeaconModel> iBeaconList = new ArrayList<IBeaconModel>();

	public interface DataOutOfRangeListener {
		public void getClose(List<IBeaconModel> ib);
		public void getFar();
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
				s = formatString(s);
				System.out.println("--------------开始打印一组数据-----------------");
				System.out.println(s);
				try {
					JSONObject jsonObj = JSONObject.fromObject(s);
					// 取得data数据
					data = jsonObj.getString("data");
					//判断节点是否已经入网，正确传输数据
					if(data.equals("0000000000ff")){
						System.out.println("节点还没入网");
					}else{
						// dataCount数据组数
						String dataCount = data.substring(4, 6);
						// 得到除去数据头的有效数据字符串dataWithoutHead
						String dataWithoutHead = data.substring(8, data.length());
						for (int i = 0; i < dataWithoutHead.length(); i = i + 10) {
							String data10 = dataWithoutHead.substring(i, i + 10);
							IBeaconModel ib = new IBeaconModel();
							ib.setUuid(data10.substring(0, 8));
							ib.setRssi(data10.substring(8));
							ib.setDistance(calDistance(Integer.parseInt(
									ib.getRssi(), 16)));
							iBeaconList.add(ib);
						}
						List<IBeaconModel> transformList = new ArrayList<IBeaconModel>();
						for (IBeaconModel iBeaconModel : iBeaconList) {
							System.out.println("UUID=" + iBeaconModel.getUuid()
									+ " RSSI=" + iBeaconModel.getRssi() + " Distance="
									+ iBeaconModel.getDistance());
							if (iBeaconModel.getDistance() < 5) {
								transformList.add(iBeaconModel);
							} 
						}
						if(transformList !=null && transformList.size() != 0){
							if (outOfRangeListener != null)
								outOfRangeListener.getClose(transformList);
						}else{
							if (outOfRangeListener != null)
								outOfRangeListener.getFar();
						}
						iBeaconList.clear();
						System.out.println("--------------打印此组数据结束-----------------");
						System.out.println();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		timer.schedule(task, 0, 3000);
	}

	/**
	 * 计算距离函数
	 * rrsi的范围在 21-53 和 55~77 之间有效
	 * @param rrsi
	 * @return
	 */
	public double calDistance(int rrsi) {
		double distance = 0;
//		System.out.println("rrsi = " + rrsi);
		int A = rrsi - 54;
//		System.out.println("A = " + A + " ");
		double n = 0;
		if (A <= 4) {
			n = 1.1;
		} else if (A > 4 && A <= 7) {
			n = 1.3;
		}  else if (A > 7 && A <= 12) {
			n = 1.6;
		}  else if (A > 12 && A <= 15) {
			n = 1.85;
		}  else if (A > 15 && A <= 18) {
			n = 2.1;
		}  else if (A > 18 && A <= 23) {
			n = 2.3;
		}  else {
			n = 2.5;
		}
		distance = Math.pow(10, (double) A / (10 * n));
		return distance;
	}

	public static void main(String[] args) {
		HttpRequestDao2 httpDao = new HttpRequestDao2(Utils.URL, Utils.PARAM2);
	}

	/**
	 * 将从服务器得到的数据格式编程可解析的Json字符串，去掉前后的""和中间的各种\
	 * @param s
	 * @return
	 */
	public String formatString(String s) {
		return s.substring(1, s.length() - 1).replace("\\", "");
	}

}
