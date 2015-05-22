package dk.kea.swc.cadd.delivery.model;

public class Truck {
	private int truckID;
	private int capacity;
	private double speed;
	private boolean available;
	
	public Truck() {
		
	}
	
	public Truck(int truckID, int capacity, double speed, boolean available) {
		this.setTruckID(truckID);
		this.capacity = capacity;
		this.speed = speed;
		this.available = available;
	}

	public int getTruckID() {
		return truckID;
	}

	public void setTruckID(int truckID) {
		this.truckID = truckID;
	}
	
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	
}
