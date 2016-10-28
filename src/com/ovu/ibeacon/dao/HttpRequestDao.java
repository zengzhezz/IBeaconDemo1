package com.ovu.ibeacon.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.ovu.ibeacon.model.IBeaconModel;
import com.ovu.ibeacon.utils.HttpRequest;

public class HttpRequestDao {

	private final static String URL = "http://120.55.95.87/api//v1/application/data";
	private final static String PARAM = "mac=AABBCC6666442216&token=70B3D5FFFE88B03A&appeui=0102030405060708";
	private List<IBeaconModel> iBeaconList = new ArrayList<IBeaconModel>();

	public interface DataOutOfRangeListener {
		public void getClose(IBeaconModel ib);

		public void getFar(IBeaconModel ib);
	}

	private DataOutOfRangeListener outOfRangeListener;

	public void setDataOutOfRangeListener(
			DataOutOfRangeListener outOfRangeListener) {
		this.outOfRangeListener = outOfRangeListener;
	}

	public HttpRequestDao(final String url, final String param) {
		// 设置时间线程，向服务器访问数据
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				String data = null;
				HttpRequest hr = new HttpRequest();
				String s = hr.sendGet(url, param);
				s = formatString(s);
				System.out.println(s);
				try {
					JSONObject jsonObj = JSONObject.fromObject(s);
					// 取得data数据
					data = jsonObj.getString("data");
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
				} catch (JSONException e) {
					e.printStackTrace();
				}
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
		};
		timer.schedule(task, 0, 1000);
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
		HttpRequestDao htpDao = new HttpRequestDao(URL, PARAM);
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
