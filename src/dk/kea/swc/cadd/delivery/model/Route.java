package dk.kea.swc.cadd.delivery.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Route {
	private IntegerProperty routeID;
	private IntegerProperty driverID;
	private IntegerProperty truckID;
	private BooleanProperty finished;
	
	public Route() {
		this.routeID 	= new SimpleIntegerProperty();
		this.driverID 	= new SimpleIntegerProperty();
		this.truckID 	= new SimpleIntegerProperty();
		this.finished 	= new SimpleBooleanProperty(false);
	}
	
	/**
	 * Constructor with all the data.
	 * @param routeID
	 * @param driverID
	 * @param truckID
	 */
	public Route(Integer routeID, Integer driverID, Integer truckID, Boolean finished) {
		this.routeID = new SimpleIntegerProperty(routeID);
		this.driverID = new SimpleIntegerProperty(driverID);
		this.truckID = new SimpleIntegerProperty(truckID);
		this.finished = new SimpleBooleanProperty(finished);
	}

	//Setters
	public void setRouteID(Integer routeID) {
		this.routeID.set(routeID);
	}
	
	public void setDriverID(Integer driverID) {
		this.driverID.set(driverID);
	}
	
	public void setTruckID(Integer truckID) {
		this.truckID.set(truckID);
	}
	
	public void setFinished(Boolean finished) {
		this.finished.set(finished);
	}
	
	//Getters
	public Integer getRouteID() {
		return routeID.get();
	}
	
	public Integer getDriverID() {
		return driverID.get();
	}
	
	public Integer getTruckID() {
		return truckID.get();
	}
	
	public Boolean isFinished() {
		return finished.get();
	}
	
	//Getters for Property
	public IntegerProperty routeIDProperty() {
		return routeID;
	}
	
	public IntegerProperty driverIDProperty() {
		return driverID;
	}
	
	public IntegerProperty truckIDProperty() {
		return truckID;
	}
	
	public BooleanProperty finishedProperty() {
		return finished;
	}
	
}
