package com.ovu.ibeacon.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.ovu.ibeacon.model.IBeaconModel;
import com.ovu.ibeacon.utils.HttpRequest;
import com.ovu.ibeacon.utils.Utils;

public class HttpRequestThread extends Thread{

	private List<IBeaconModel> iBeaconList = new ArrayList<IBeaconModel>();
	
	private String url;
	private String param;

	public interface DataOutOfRangeListener {
		public void getClose(IBeaconModel ib);
		public void getFar(IBeaconModel ib);
	}

	private DataOutOfRangeListener outOfRangeListener;

	public void setDataOutOfRangeListener(
			DataOutOfRangeListener outOfRangeListener) {
		this.outOfRangeListener = outOfRangeListener;
	}

	public HttpRequestThread(final String url, final String param) {
		this.url = url;
		this.param = param;
	}
	
	@Override
	public void run() {
		String data = null;
		HttpRequest hr = new HttpRequest();
		while(true){
			try {
				String s = hr.sendGet(url, param);
				s = formatString(s);
				System.out.println(s);
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
						for (IBeaconModel iBeaconModel : iBeaconList) {
							System.out.println("UUID=" + iBeaconModel.getUuid()
									+ " RSSI=" + iBeaconModel.getRssi() + " Distance="
									+ iBeaconModel.getDistance());
							if (iBeaconModel.getDistance() < 10) {
								if (outOfRangeListener != null)
									outOfRangeListener.getClose(iBeaconModel);
							} else {
								if (outOfRangeListener != null)
									outOfRangeListener.getFar(iBeaconModel);
							}
						}
						iBeaconList.clear();
					}
				}
				sleep(1000);
			} catch (JSONException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 计算距离函数
	 * @param rrsi
	 * @return
	 */
	public double calDistance(int rrsi) {
		double distance = 0;
		int A = rrsi - 52;
		double n = 0;
		if (A > 1 && A <= 4) {
			n = 1.3;
		} else if (A > 4 && A <= 7) {
			n = 1.75;
		} else if (A > 7 && A <= 12) {
			n = 2.10;
		} else if (A > 12 && A <= 15) {
			n = 2.5;
		} else if (A > 15 && A <= 18) {
			n = 2.75;
		} else if (A > 18 && A <= 23) {
			n = 3.3;
		} else {
			return 100;
		}
		System.out.println(A);
		distance = Math.pow(10, (double) A / (10 * n));
		return distance;
	}

	public static void main(String[] args) {
		HttpRequestThread htpDao = new HttpRequestThread(Utils.URL, Utils.PARAM14);
		htpDao.start();
		double distance = htpDao.calDistance(67);
		System.out.println(distance);
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
