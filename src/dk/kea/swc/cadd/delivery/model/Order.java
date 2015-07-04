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
	private StringProperty 	storageName;
	private IntegerProperty routeID;
	private DoubleProperty quantity;
	private DoubleProperty price;
	private DoubleProperty profit;
	
	public Order() {
		this.orderID 	= new SimpleIntegerProperty();
		this.cityName 	= new SimpleStringProperty();
		this.storageName = new SimpleStringProperty();
		this.routeID 	= new SimpleIntegerProperty();
		this.quantity 	= new SimpleDoubleProperty();
		this.price 	= new SimpleDoubleProperty();
		this.profit 	= new SimpleDoubleProperty();
	}
	
	/**
	 * Constructor with all the data.
	 * @param orderID
	 * @param cityName
	 * @param routeID
	 * @param quantity
	 */
	public Order(Integer orderID, String cityName, String storageName, Integer routeID, Double quantity, Double price, Double profit) {
		this.orderID 	= new SimpleIntegerProperty(orderID);
		this.cityName 	= new SimpleStringProperty(cityName);
		this.storageName 	= new SimpleStringProperty(storageName);
		this.routeID 	= new SimpleIntegerProperty(routeID);
		this.quantity 	= new SimpleDoubleProperty(quantity);
		this.price 		= new SimpleDoubleProperty(price);
		this.profit 	= new SimpleDoubleProperty(profit);
	}

	//Setters
	public void setOrderID(Integer orderID) {
		this.orderID.set(orderID);
	}
		
	public void setCityName(String cityName) {
		this.cityName.set(cityName);
	}
	
	public void setStorageName(String storageName) {
		this.storageName.set(storageName);
	}
	
	public void setRouteID(Integer routeID) {
		this.routeID.set(routeID);
	}
	
	public void setQuantity(Double quantity) {
		this.quantity.set(quantity);
	}
	
	public void setPrice(Double price) {
		this.price.set(price);
	}
	
	public void setProfit(Double profit) {
		this.profit.set(profit);
	}
	
	//Getters
	public Integer getOrderID() {
		return orderID.get();
	}
	
	public String getCityName() {
		return cityName.get();
	}
	
	public String getStorageName() {
		return storageName.get();
	}
	
	public Integer getRouteID() {
		return routeID.get();
	}
	
	public Double getQuantity() {
		return quantity.get();
	}
	
	public Double getPrice() {
		return price.get();
	}
	
	public Double getProfit() {
		return profit.get();
	}
	
	//Property Getters
	public IntegerProperty orderIDProperty() {
		return orderID;
	}
	
	public StringProperty cityNameProperty() {
		return cityName;
	}
	
	public StringProperty storageNameProperty() {
		return storageName;
	}

	public IntegerProperty routeIDProperty() {
		return routeID;
	}

	public DoubleProperty quantityProperty() {
		return quantity;
	}
	
	public DoubleProperty priceProperty() {
		return price;
	}
	
	public DoubleProperty profitProperty() {
		return profit;
	}

	@Override
	public String toString() {
		return cityName + " " + orderID + " " + quantity + " tones";
	}

}
