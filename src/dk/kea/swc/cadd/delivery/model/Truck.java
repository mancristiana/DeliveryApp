package dk.kea.swc.cadd.delivery.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Truck {
	private IntegerProperty truckID;
	private IntegerProperty capacity;
	private DoubleProperty speed;
	private BooleanProperty available;
	
	public Truck() {
		this.truckID = new SimpleIntegerProperty();
		this.capacity = new SimpleIntegerProperty();
		this.speed = new SimpleDoubleProperty();
		this.available = new SimpleBooleanProperty();
	}
	
	public Truck(int truckID, int capacity, double speed, boolean available) {
		
		this.truckID = new SimpleIntegerProperty(truckID);
		this.capacity = new SimpleIntegerProperty(capacity);
		this.speed = new SimpleDoubleProperty(speed);
		this.available = new SimpleBooleanProperty(available);
		
	}

	//Setters
	
	public void setTruckID(Integer truckID){
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
	
	public Integer getTruckID(){
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
	
	public IntegerProperty truckIDProperty(){
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
