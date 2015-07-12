package dk.kea.swc.cadd.delivery.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Truck {
	private StringProperty truckID;
	private IntegerProperty capacity;
	private DoubleProperty speed;
	private BooleanProperty available;
	
	public Truck() {
		this.truckID = new SimpleStringProperty();
		this.capacity = new SimpleIntegerProperty();
		this.speed = new SimpleDoubleProperty();
		this.available = new SimpleBooleanProperty();
	}
	
	public Truck(String truckID, int capacity, double speed, boolean available) {
		
		this.truckID = new SimpleStringProperty(truckID);
		this.capacity = new SimpleIntegerProperty(capacity);
		this.speed = new SimpleDoubleProperty(speed);
		this.available = new SimpleBooleanProperty(available);
		
	}

	//Setters
	
	public void setTruckID(String truckID){
		this.truckID.set(truckID);
	}
	
	public void setCapacity(Integer capacity){
		this.capacity.set(capacity);
	}
	
	public void setSpeed(Double speed){
		this.speed.set(speed);
	}
	
	public void setAvailable(Boolean available){
		this.available.set(available);
	}
	
	//Getters
	
	public String getTruckID(){
		return truckID.get();
	}
	
	public Integer getCapacity(){
		return capacity.get();
	}
	
	public Double getSpeed(){
		return speed.get();
	}
	
	public Boolean getAvailable(){
		return available.get();
	}
	
	//Property Getters
	
	public StringProperty truckIDProperty(){
		return truckID;
	}
	
	public IntegerProperty capacityProperty(){
		return capacity;
	}
	
	public DoubleProperty speedProperty(){
		return speed;
	}
	
	public BooleanProperty availableProperty(){
		return available;
	}
	
	@Override
	public String toString() {
		return truckID + " " + capacity + " " +speed + " " + available;
	}

	

	
}
