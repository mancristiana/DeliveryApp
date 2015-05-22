package dk.kea.swc.cadd.delivery.model;

public class Order {
	private int orderID;
	private int storageID;
	private int locationID;
	private int routeID;
	
	public Order() {

	}
	
	public Order(int orderID, int storageID, int locationID, int routeID) {
		this.setOrderID(orderID);
		this.setStorageID(storageID);
		this.setLocationID(locationID);
		this.setRouteID(routeID);
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getStorageID() {
		return storageID;
	}

	public void setStorageID(int storageID) {
		this.storageID = storageID;
	}

	public int getRouteID() {
		return routeID;
	}

	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}

	public int getLocationID() {
		return locationID;
	}

	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}


}
