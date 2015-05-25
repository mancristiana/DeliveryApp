package dk.kea.swc.cadd.delivery.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Order {
	private IntegerProperty orderID;
	private IntegerProperty storageID;
	private IntegerProperty locationID;
	private IntegerProperty routeID;
	
	public Order() {

	}
	
	public Order(Integer orderID, Integer storageID, Integer locationID, Integer routeID) {
		this.orderID 	= new SimpleIntegerProperty(orderID);
		this.storageID 	= new SimpleIntegerProperty(storageID);
		this.locationID = new SimpleIntegerProperty(locationID);
		this.routeID 	= new SimpleIntegerProperty(routeID);
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
	
	//Property Getters
	public IntegerProperty getOrderIDProperty() {
		return orderID;
	}
	public IntegerProperty getStorageIDProperty() {
		return storageID;
	}

	public IntegerProperty getLocationIDProperty() {
		return locationID;
	}

	public IntegerProperty getRouteIDProperty() {
		return routeID;
	}

	

	

}
