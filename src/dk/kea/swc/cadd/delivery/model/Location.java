package dk.kea.swc.cadd.delivery.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Location {
	
	private StringProperty 	cityName;
	private StringProperty 	storageName;
	private DoubleProperty 	price;
	
	public Location() {
		
	}
	
	/** 
     * Constructor with all the data.
     * 
     * @param cityName
     * @param storageName
     * @param price
     */
	public Location(String cityName,String storageName,Double price) {
		this.cityName 		= new SimpleStringProperty(cityName);
		this.storageName 	= new SimpleStringProperty(storageName);
		this.price 			= new SimpleDoubleProperty(price);
	}
    
	//Setters	
	public void setCityName(String cityName) {
		this.cityName.set(cityName);
	}

	public void setStorageName(String storageName) {
		this.storageName.set(storageName);
	}
	
	public void setPrice(Double price) {
		this.price.set(price);
	}

	//Getters
	public String getCityName() {
		return cityName.get();
	}

	public String getStorageName() {
		return storageName.get();
	}
	
	public Double getPrice() {
		return price.get();
	}
	
	//Getters for Property
	public StringProperty cityNameProperty() {
		return cityName;
	}
	
	public StringProperty storageNameProperty() {
		return storageName;
	}
	
	public DoubleProperty priceProperty() {
		return price;
	}
	
}
