package dk.kea.swc.cadd.delivery.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import dk.kea.swc.cadd.delivery.model.Truck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TruckDAO {
	   
	   public static ObservableList<Truck> getTrucks(){
	    ObservableList<Truck> list = FXCollections.observableArrayList();
	   
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
	    	String sql = "SELECT * FROM truck ORDER BY truck_id";
	    	PreparedStatement stmt = connection.prepareStatement(sql);
	    	stmt.executeQuery();
	    	
	    	ResultSet rs = stmt.getResultSet();
	    	while(rs.next()) {
	    		Integer truckID     = rs.getInt("truck_id");
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
			   String sql = "INSERT INTO `cadd`.`truck` (`truck_id`, `capacity`, `speed`, `available`) VALUES (NULL, ?, ?, ?);";
			   PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			   stmt.setInt(1, truck.getCapacity());
			   stmt.setDouble(2, truck.getSpeed());
			   stmt.setBoolean(3, truck.getAvailable());
			   stmt.execute();
			   ResultSet rs = stmt.getGeneratedKeys();
			   if(rs.next())
				   truck.setTruckID(rs.getInt(1));
			   return null;
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
			   stmt.setInt(1, truck.getTruckID());
			   stmt.execute();
			   return null;
		   } catch (SQLException e){
			   return e.getErrorCode() + " " + e.getMessage();
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
			   stmt.setInt(2, truck.getTruckID());
			   stmt.execute();
			   return null;
		   } catch (SQLException e){
			   return e.getErrorCode() + " " + e.getMessage();
		   }
	   }
}
