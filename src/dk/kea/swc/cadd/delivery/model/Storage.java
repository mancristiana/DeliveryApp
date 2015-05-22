package dk.kea.swc.cadd.delivery.model;

public class Storage {
	private int storageID;
	private String cityName;
	private int availableQuantity;
	private String latitude;
	private String longitude;
	
	public Storage() {
		
	}
	
	public Storage(int storageID, String cityName, int availableQuantity, String latitude, String longitude) {
		this.storageID = storageID;
		this.cityName = cityName;
		this.availableQuantity = availableQuantity;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public int getStorageID() {
		return storageID;
	}
	public void setStorageID(int id) {
		this.storageID = id;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public int getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
}
