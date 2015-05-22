package dk.kea.swc.cadd.delivery.model;

public class Driver {
	private int driverID;
	private String name;
	private String phone;
	private String email;
	private boolean available;
	
	public Driver() {
		
	}
	
	public Driver(int driverID, String name, String phone, String email, boolean available) {
		this.setDriverID(driverID);
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.available = available;
	}
	
	public int getDriverID() {
		return driverID;
	}

	public void setDriverID(int driverID) {
		this.driverID = driverID;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	
	
}
