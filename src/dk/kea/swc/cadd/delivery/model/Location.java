package dk.kea.swc.cadd.delivery.model;

public class Location {
	private int locationID;
	private String cityName;
	private double price;
	private String latitude;
	private String longitude;
	
	public Location() {
		
	}
	
	public Location(int id, String cityName, double price, String latitude, String longitude) {
		this.setLocationID(id);
		this.cityName = cityName;
		this.price = price;
		this.latitude = latitude;
		this.longitude = longitude;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getLocationID() {
		return locationID;
	}

	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
}
