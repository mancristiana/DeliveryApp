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
	private StringProperty 	latitude;
	private StringProperty 	longitude;
	
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
	public Location(Integer locationID, String cityName, Double price,String latitude, String longitude) {
		this.locationID = new SimpleIntegerProperty(locationID);
		this.cityName 	= new SimpleStringProperty(cityName);
		this.price 		= new SimpleDoubleProperty(price);
		this.latitude 	= new SimpleStringProperty(latitude);
		this.longitude 	= new SimpleStringProperty(longitude);
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

	public void setLatitude(String latitude) {
		this.latitude.set(latitude);
	}

	public void setLongitude(String longitude) {
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

	public String getLatitude() {
		return latitude.get();
	}

	public String getLongitude() {
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

	public StringProperty latitudeProperty() {
		return latitude;
	}

	public StringProperty longitudeProperty() {
		return longitude;
	}
	
}
