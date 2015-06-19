package dk.kea.swc.cadd.delivery.model;

import java.time.LocalDate;

import dk.kea.swc.cadd.delivery.db.DriverDAO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Route {
	private IntegerProperty routeID;
	private IntegerProperty driverID;
	private IntegerProperty truckID;
	private BooleanProperty finished;
	private Driver driver;
	private LocalDate date;
	
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
		this.routeID 	= new SimpleIntegerProperty(routeID);
		this.driverID 	= new SimpleIntegerProperty(driverID);
		this.truckID 	= new SimpleIntegerProperty(truckID);
		this.finished 	= new SimpleBooleanProperty(finished);
		this.driver		= new DriverDAO().getDriverByID(driverID);
	}

	//Setters
	public void setRouteID(Integer routeID) {
		this.routeID.set(routeID);
	}
	
	public void setDriverID(Integer driverID) {
		this.driverID.set(driverID);
		this.driver	= new DriverDAO().getDriverByID(driverID);
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

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	public StringProperty driverProperty() {
		return new SimpleStringProperty(driver.toString());
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
}
