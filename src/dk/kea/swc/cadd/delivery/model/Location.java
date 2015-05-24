package dk.kea.swc.cadd.delivery.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Location {
	private IntegerProperty locationID;
	private StringProperty 	cityName;
	private DoubleProperty 	price;
	private StringProperty 	latitude;
	private StringProperty 	longitude;
	
	public Location() {
		
	}
	
	public Location(IntegerProperty locationID, StringProperty cityName, DoubleProperty price,StringProperty latitude, StringProperty longitude) {
		this.locationID = locationID;
		this.cityName 	= cityName;
		this.price 		= price;
		this.latitude 	= latitude;
		this.longitude 	= longitude;
	}

	public IntegerProperty getLocationID() {
		return locationID;
	}

	public void setLocationID(IntegerProperty locationID) {
		this.locationID = locationID;
	}

	public StringProperty getCityName() {
		return cityName;
	}

	public void setCityName(StringProperty cityName) {
		this.cityName = cityName;
	}

	public DoubleProperty getPrice() {
		return price;
	}

	public void setPrice(DoubleProperty price) {
		this.price = price;
	}

	public StringProperty getLatitude() {
		return latitude;
	}

	public void setLatitude(StringProperty latitude) {
		this.latitude = latitude;
	}

	public StringProperty getLongitude() {
		return longitude;
	}

	public void setLongitude(StringProperty longitude) {
		this.longitude = longitude;
	}
	
}
