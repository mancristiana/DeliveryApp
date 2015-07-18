package dk.kea.swc.cadd.delivery.model;

import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Route {
	private IntegerProperty routeID;
	private StringProperty 	truckID;
	private BooleanProperty finished;
	private DoubleProperty 	totalProfit;
	private Driver driver;
	private LocalDate date;
	
	public Route() {
		this.routeID 	= new SimpleIntegerProperty();
		this.truckID 	= new SimpleStringProperty();
		this.finished 	= new SimpleBooleanProperty(false);
	}
	public Route(Integer routeID, Driver driver, String truckID, Boolean finished) {
		this(routeID, LocalDate.now(), driver, truckID, finished, 0.0);
	}
	/**
	 * Constructor with all the data.
	 * @param routeID
	 * @param driverID
	 * @param truckID
	 */
	public Route(Integer routeID, LocalDate date, Driver driver, String truckID, Boolean finished, Double totalProfit) {
		this.routeID 	= new SimpleIntegerProperty(routeID);
		this.date		= date;
		this.truckID 	= new SimpleStringProperty(truckID);
		this.finished 	= new SimpleBooleanProperty(finished);
		this.driver		= driver;
		this.totalProfit = new SimpleDoubleProperty(totalProfit);
	}

	//Setters
	public void setRouteID(Integer routeID) {
		this.routeID.set(routeID);
	}

	public void setTruckID(String truckID) {
		this.truckID.set(truckID);
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	 
	public void setFinished(Boolean finished) {
		this.finished.set(finished);
	}
	
	public void setTotalProfit(Double totalProfit) {
		this.totalProfit.set(totalProfit);
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	//Getters
	public Integer getRouteID() {
		return routeID.get();
	}
	
	public String getTruckID() {
		return truckID.get();
	}
	
	public Driver getDriver() {
		return driver;
	}
	
	public Boolean isFinished() {
		return finished.get();
	}
	
	public Double getTotalProfit() {
		return totalProfit.get();
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	//Getters for Property
	public IntegerProperty routeIDProperty() {
		return routeID;
	}

	public StringProperty truckIDProperty() {
		return truckID;
	}
	
	public StringProperty driverProperty() {
		return new SimpleStringProperty(driver.toString());
	}

	public BooleanProperty finishedProperty() {
		return finished;
	}
	
	public DoubleProperty totalProfitProperty() {
		return totalProfit;
	}

	public StringProperty dateProperty() {
		return new SimpleStringProperty(date.toString());
	}
	
}
