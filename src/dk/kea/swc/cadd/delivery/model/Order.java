package dk.kea.swc.cadd.delivery.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Order {
	private IntegerProperty orderID;
	private StringProperty 	cityName;
	private IntegerProperty routeID;
	private DoubleProperty quantity;
	
	public Order() {
		this.orderID 	= new SimpleIntegerProperty();
		this.cityName 	= new SimpleStringProperty();
		this.routeID 	= new SimpleIntegerProperty();
		this.quantity 	= new SimpleDoubleProperty();
	}
	
	/**
	 * Constructor with all the data.
	 * @param orderID
	 * @param cityName
	 * @param routeID
	 * @param quantity
	 */
	public Order(Integer orderID, String cityName, Integer routeID, Double quantity) {
		this.orderID 	= new SimpleIntegerProperty(orderID);
		this.cityName 	= new SimpleStringProperty(cityName);
		this.routeID 	= new SimpleIntegerProperty(routeID);
		this.quantity 	= new SimpleDoubleProperty(quantity);
	}

	//Setters
	public void setOrderID(Integer orderID) {
		this.orderID.set(orderID);
	}
		
	public void setCityName(String cityName) {
		this.cityName.set(cityName);
	}
	
	public void setRouteID(Integer routeID) {
		this.routeID.set(routeID);
	}
	
	public void setQuantity(Double quantity) {
		this.quantity.set(quantity);
	}
	
	//Getters
	public Integer getOrderID() {
		return orderID.get();
	}
	
	public String getCityName() {
		return cityName.get();
	}
	
	public Integer getRouteID() {
		return routeID.get();
	}
	
	public Double getQuantity() {
		return quantity.get();
	}
	
	//Property Getters
	public IntegerProperty orderIDProperty() {
		return orderID;
	}
	
	public StringProperty cityNameProperty() {
		return cityName;
	}

	public IntegerProperty routeIDProperty() {
		return routeID;
	}

	public DoubleProperty quantityProperty() {
		return quantity;
	}

	@Override
	public String toString() {
		return cityName + " " + orderID + " " + quantity + " tones";
	}

}
