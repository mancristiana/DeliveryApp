package dk.kea.swc.cadd.delivery.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Storage {
	private StringProperty cityName;
	private IntegerProperty availableQuantity;
	
	
	public Storage() {
		this.cityName = new SimpleStringProperty();
		this.availableQuantity = new SimpleIntegerProperty();
	}
	
	public Storage(String cityName, Integer availableQuantity) {
		this.cityName = new SimpleStringProperty (cityName);
		this.availableQuantity = new SimpleIntegerProperty (availableQuantity);
		
	}
	
	//Setters
	public void setCityName(String cityName) {
		this.cityName.set(cityName);
	}
	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity.set(availableQuantity);
	}
	
	//Getters
	public String getCityName() {
		return cityName.get();
	}
	public Integer getAvailableQuantity() {
		return availableQuantity.get();
	}
	
	//Property Getters
	public StringProperty cityNameProperty() {
		return cityName;
	}
	public IntegerProperty availableQuantityProperty() {
		return availableQuantity;
	}
}
