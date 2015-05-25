package dk.kea.swc.cadd.delivery.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Order {
	private IntegerProperty orderID;
	private IntegerProperty storageID;
	private IntegerProperty locationID;
	private IntegerProperty routeID;
	private DoubleProperty quantity;
	
	public Order() {

	}
	
	public Order(Integer orderID, Integer storageID, Integer locationID, Integer routeID, Double quantity) {
		this.orderID 	= new SimpleIntegerProperty(orderID);
		this.storageID 	= new SimpleIntegerProperty(storageID);
		this.locationID = new SimpleIntegerProperty(locationID);
		this.routeID 	= new SimpleIntegerProperty(routeID);
		this.quantity 	= new SimpleDoubleProperty(quantity);
	}

	//Setters
	public void setOrderID(Integer orderID) {
		this.orderID.set(orderID);
	}
	
	public void setStorageID(Integer storageID) {
		this.storageID.set(storageID);
	}
	
	public void setLocationID(Integer locationID) {
		this.locationID.set(locationID);
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
	
	public Integer getLocationID() {
		return locationID.get();
	}
	
	public Integer getStorageID() {
		return storageID.get();
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
	public IntegerProperty storageIDProperty() {
		return storageID;
	}

	public IntegerProperty locationIDProperty() {
		return locationID;
	}

	public IntegerProperty routeIDProperty() {
		return routeID;
	}

	public DoubleProperty quantityProperty() {
		return quantity;
	}

	

}
