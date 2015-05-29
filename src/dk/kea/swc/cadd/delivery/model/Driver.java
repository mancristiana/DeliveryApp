package dk.kea.swc.cadd.delivery.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Driver {
	
	private IntegerProperty driverId;
	private StringProperty 	name;
	private StringProperty 	phone;
	private StringProperty 	email;
	private BooleanProperty available;
	
	public Driver() {
		
	}
	
	/**
     * Constructor with all the data.
     * 
     * @param driverId
     * @param name
     * @param phone
     * @param email
     * @param available
     */
	public Driver(Integer driverId,String name,String phone,String email,Boolean available) {
		this.driverId 	= new SimpleIntegerProperty(driverId);
		this.name 		= new SimpleStringProperty(name);
		this.phone 		= new SimpleStringProperty(phone);
		this.email 		= new SimpleStringProperty(email);
		this.available 	= new SimpleBooleanProperty(available);
	}
    
	//Setters	
	public void setDriverId(Integer driverId) {
		this.driverId.set(driverId);
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public void setPhone(String phone) {
		this.phone.set(phone);
	}
	
	public void setEmail(String email) {
		this.email.set(email);
	}
	
	public void setAvailable(Boolean available) {
		this.available.set(available);
	}
	
	//Getters
	public Integer getDriverId() {
		return driverId.get();
	}

	public String getName() {
		return name.get();
	}

	public String getPhone() {
		return phone.get();
	}
	
	public String getEmail() {
		return email.get();
	}
	
	public Boolean getAvailable() {
		return available.get();
	}
	
	//Getters for Property
	public IntegerProperty driverIdProperty() {
		return driverId;
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public StringProperty phoneProperty() {
		return phone;
	}
	
	public StringProperty emailProperty() {
		return email;
	}
	
	public BooleanProperty availableProperty() {
		return available;
	}
	
}
