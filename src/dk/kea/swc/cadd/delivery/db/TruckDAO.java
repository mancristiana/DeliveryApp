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
	   
	   private final Connection con;

	   public TruckDAO() {
		   con = DBConnector.getConnection();
	   }
	   
	   public ObservableList<Truck> getTrucks(){
	    ObservableList<Truck> list = FXCollections.observableArrayList();
	   
	    try {
	    	String sql = "SELECT * FROM truck ORDER BY truck_id";
	    	PreparedStatement stmt = con.prepareStatement(sql);
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
	   
	   public String createTruck(Truck truck) {
		   try {
			   String sql = "INSERT INTO `cadd`.`truck` (`truck_id`, `capacity`, `speed`, `available`) VALUES (NULL, ?, ?, ?);";
			   PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
	   
	   public String removeTruck(Truck truck) {
		   try {
			   String sql = "DELETE FROM `cadd`.`truck` WHERE `truck`.`truck_id` = ?";
			   PreparedStatement stmt = con.prepareStatement(sql);
			   stmt.setInt(1, truck.getTruckID());
			   stmt.execute();
			   return null;
		   } catch (SQLException e){
			   return e.getErrorCode() + " " + e.getMessage();
		   }
	   }
	   public String updateTruck(Truck truck){
		   try {
			   String sql = "UPDATE  `cadd`.`truck` "
			   		+ "SET  `available` = ? "
			   		+ "WHERE  `truck`.`truck_id` = ?;";
			   PreparedStatement stmt = con.prepareStatement(sql);
			   stmt.setBoolean(1, truck.getAvailable());
			   stmt.setInt(2, truck.getTruckID());
			   stmt.execute();
			   return null;
		   } catch (SQLException e){
			   return e.getErrorCode() + " " + e.getMessage();
		   }
	   }
}
