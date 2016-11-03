package com.ovu.ibeacon.model;

public class IBeaconModel {
	
	private String uuid;
	private String rssi;
	private String name;
	private double distance;
	private int updateTime;

	/**
	 * 无参数构造方法
	 */
	public IBeaconModel(){
		
	}
	
	/**
	 * 带 uuid 和  distance 的构造方法
	 * @param uuid
	 * @param distance
	 */
	public IBeaconModel(String uuid, double distance) {
		super();
		this.uuid = uuid;
		this.distance = distance;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRssi() {
		return rssi;
	}

	public void setRssi(String rssi) {
		this.rssi = rssi;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(int updateTime) {
		this.updateTime = updateTime;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof IBeaconModel){
			IBeaconModel beacon = (IBeaconModel) obj;
			if(this.uuid.equals(beacon.getUuid()))
				return true;
		}
		return false;
	}
}
