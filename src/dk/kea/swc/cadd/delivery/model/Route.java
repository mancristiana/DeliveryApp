package dk.kea.swc.cadd.delivery.model;

public class Route {
	private int routeID;
	private int driverID;
	private int truckID;
	private boolean finished;
	
	public Route() {

	}
	
	public Route(int routeID, int driverID, int truckID) {
		this.setRouteID(routeID);
		this.setDriverID(driverID);
		this.setTruckID(truckID);
		finished = false;
	}

	public int getRouteID() {
		return routeID;
	}

	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}

	public int getDriverID() {
		return driverID;
	}

	public void setDriverID(int driverID) {
		this.driverID = driverID;
	}

	public int getTruckID() {
		return truckID;
	}

	public void setTruckID(int truckID) {
		this.truckID = truckID;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
}
