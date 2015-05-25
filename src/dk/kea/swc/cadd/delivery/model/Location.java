package dk.kea.swc.cadd.delivery.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Location {
	private IntegerProperty locationID;
	private StringProperty 	cityName;
	private DoubleProperty 	price;
	private DoubleProperty 	latitude;
	private DoubleProperty 	longitude;
	
	public Location() {
		
	}
	
	/**
     * Constructor with complete data.
     * 
     * @param locationID
     * @param cityName
     * @param price
     * @param latitude
     * @param longitude
     */
	public Location(Integer locationID, String cityName, Double price,Double latitude, Double longitude) {
		this.locationID = new SimpleIntegerProperty(locationID);
		this.cityName 	= new SimpleStringProperty(cityName);
		this.price 		= new SimpleDoubleProperty(price);
		this.latitude 	= new SimpleDoubleProperty(latitude);
		this.longitude 	= new SimpleDoubleProperty(longitude);
	}
    
	//Setters
	public void setLocationID(Integer locationID) {
		this.locationID.set(locationID);
	}
	
	public void setCityName(String cityName) {
		this.cityName.set(cityName);
	}

	public void setPrice(Double price) {
		this.price.set(price);
	}

	public void setLatitude(Double latitude) {
		this.latitude.set(latitude);
	}

	public void setLongitude(Double longitude) {
		this.longitude.set(longitude);
	}

	//Getters
	public Integer getLocationID() {
		return locationID.get();
	}
	
	public String getCityName() {
		return cityName.get();
	}

	public Double getPrice() {
		return price.get();
	}

	public Double getLatitude() {
		return latitude.get();
	}

	public Double getLongitude() {
		return longitude.get();
	}
	
	//Getters for Property
	public IntegerProperty locationIDProperty() {
		return locationID;
	}
	
	public StringProperty cityNameProperty() {
		return cityName;
	}

	public DoubleProperty priceProperty() {
		return price;
	}

	public DoubleProperty latitudeProperty() {
		return latitude;
	}

	public DoubleProperty longitudeProperty() {
		return longitude;
	}
	
}
