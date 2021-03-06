package dk.kea.swc.cadd.delivery.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dk.kea.swc.cadd.delivery.model.Truck;

public class TruckDAO {
	   
	   public static ObservableList<Truck> getTrucks(){
	    ObservableList<Truck> list = FXCollections.observableArrayList();
	   
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
	    	String sql = "SELECT * FROM truck WHERE retired = 0 ORDER BY truck_id";
	    	PreparedStatement stmt = connection.prepareStatement(sql);
	    	stmt.executeQuery();
	    	
	    	ResultSet rs = stmt.getResultSet();
	    	while(rs.next()) {
	    		String truckID     = rs.getString("truck_id");
	    		Integer capacity    = rs.getInt("capacity");
	    		Double speed        = rs.getDouble("speed");
	    		Boolean available   = rs.getBoolean("available");
	    		list.add(new Truck(truckID,capacity,speed,available));
	    	}
	    
	    }catch (SQLException e){
	    	e.printStackTrace();
	    }
	   return list;
	   
	   
	   }
	   
	   public static String createTruck(Truck truck) {
			Connection connection = null;
			try {
				connection= DBConnector.getConnection();
			   String sql = "INSERT INTO `cadd`.`truck` (`truck_id`, `capacity`, `speed`, `available`) VALUES (?, ?, ?, ?);";
			   PreparedStatement stmt = connection.prepareStatement(sql);
			   stmt.setString(1, truck.getTruckID());
			   stmt.setInt(2, truck.getCapacity());
			   stmt.setDouble(3, truck.getSpeed());
			   stmt.setBoolean(4, truck.getAvailable());
			   stmt.execute();
			   
			   return "";
		   }catch (SQLException e) {
			   return e.getErrorCode() + " " + e.getMessage();
		   }
	   }
	   
	   public static String removeTruck(Truck truck) {
			Connection connection = null;
			try {
				connection= DBConnector.getConnection();
			   String sql = "DELETE FROM `cadd`.`truck` WHERE `truck`.`truck_id` = ?";
			   PreparedStatement stmt = connection.prepareStatement(sql);
			   stmt.setString(1, truck.getTruckID());
			   stmt.execute();
			   return "";
		   } catch (SQLException e){
			   if(e.getErrorCode() == 1451) { 	// This case is when "Cannot delete or update a parent row: a foreign key constraint fails" 
					try {						// which means truck is assigned to a route
						String sql = "UPDATE   `cadd`.`truck` "
						   		+ "SET  `retired` =  1, `available` = 0 "
						   		+ "WHERE `truck`.`truck_id` = ?";
						
						PreparedStatement stmt = connection.prepareStatement(sql);
						stmt.setString(1, truck.getTruckID());
						stmt.execute();
						return "Truck has been active on routes. \nCan't delete from DB. \nIt is marked as retired instead.";
					} catch (SQLException e2) {
						return "Error code: " +e2.getErrorCode() + "\nMessage: " + e2.getMessage();
					}
					
				}
				return "Error code: " +e.getErrorCode() + "\nMessage: " + e.getMessage();
		   }
	   }
	   public static String updateTruck(Truck truck){
			Connection connection = null;
			try {
				connection= DBConnector.getConnection();
			   String sql = "UPDATE  `cadd`.`truck` "
			   		+ "SET  `available` = ? "
			   		+ "WHERE  `truck`.`truck_id` = ?;";
			   PreparedStatement stmt = connection.prepareStatement(sql);
			   stmt.setBoolean(1, truck.getAvailable());
			   stmt.setString(2, truck.getTruckID());
			   stmt.execute();
			   return "";
		   } catch (SQLException e){
			   return e.getErrorCode() + " " + e.getMessage();
		   }
	   }
}
