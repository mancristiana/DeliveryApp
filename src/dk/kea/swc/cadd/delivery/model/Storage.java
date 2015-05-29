package dk.kea.swc.cadd.delivery.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Storage {
	private IntegerProperty storageID;
	private StringProperty cityName;
	private IntegerProperty availableQuantity;
	
	
	public Storage() {
		this.storageID = new SimpleIntegerProperty();
		this.cityName = new SimpleStringProperty();
		this.availableQuantity = new SimpleIntegerProperty();
	}
	
	public Storage(Integer storageID, String cityName, Integer availableQuantity) {
		this.storageID = new SimpleIntegerProperty (storageID);
		this.cityName = new SimpleStringProperty (cityName);
		this.availableQuantity = new SimpleIntegerProperty (availableQuantity);
		
	}
	
	//Setters
	
	public void setStorageID (Integer storageID) {
		this.storageID.set(storageID);
	}
	public void setCityName(String cityName) {
		this.cityName.set(cityName);
	}
	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity.set(availableQuantity);
	}
	
	//Getters
	
	public Integer getStorageID() {
		return storageID.get();
	}
	public String getCityName() {
		return cityName.get();
	}
	public Integer getAvailableQuantity() {
		return availableQuantity.get();
	}
	
	//Property Getters
	
	public IntegerProperty storageIDProperty() {
		return storageID;
	}
	public StringProperty cityNameProperty() {
		return cityName;
	}
	public IntegerProperty availableQuantityProperty() {
		return availableQuantity;
	}
}
