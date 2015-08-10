package dk.kea.swc.cadd.delivery.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Storage {
	private StringProperty cityName;
	private DoubleProperty availableQuantity;
	
	
	public Storage() {
		this.cityName = new SimpleStringProperty();
		this.availableQuantity = new SimpleDoubleProperty();
	}
	
	public Storage(String cityName, Double availableQuantity) {
		this.cityName = new SimpleStringProperty (cityName);
		this.availableQuantity = new SimpleDoubleProperty (availableQuantity);
		
	}
	
	//Setters
	public void setCityName(String cityName) {
		this.cityName.set(cityName);
	}
	public void setAvailableQuantity(Double availableQuantity) {
		this.availableQuantity.set(availableQuantity);
	}
	
	//Getters
	public String getCityName() {
		return cityName.get();
	}
	public Double getAvailableQuantity() {
		return availableQuantity.get();
	}
	
	//Property Getters
	public StringProperty cityNameProperty() {
		return cityName;
	}
	public DoubleProperty availableQuantityProperty() {
		return availableQuantity;
	}
}
