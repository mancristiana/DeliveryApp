package dk.kea.swc.cadd.delivery.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dk.kea.swc.cadd.delivery.model.Location;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LocationDAO {

	public static ObservableList<Location> getLocations() {
		ObservableList<Location> list = FXCollections.observableArrayList();
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
            String sql = "SELECT * FROM location ORDER BY cityname";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
            	String cityName 	= rs.getString("cityname");
            	String storageName 	= rs.getString("storageName");
            	Double price 		= rs.getDouble("price");
            	list.add(new Location(cityName,storageName,price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
	
	public static Location getLocationByID(String id) {
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
            String sql = "SELECT * FROM location WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.executeQuery();
            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
            	String cityName 	= rs.getString("cityname");
            	String storageName 	= rs.getString("storageName");
            	Double price 		= rs.getDouble("price");
            	return new Location(cityName,storageName,price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
           
    }
	
	public static ObservableList<String> getLocationNames() {
		ObservableList<String> list = FXCollections.observableArrayList();
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
            String sql = "SELECT cityname FROM location WHERE 1";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.executeQuery();

       ResultSet rs = stmt.getResultSet();
        while(rs.next()) {
        	String cityName 	= rs.getString("cityname");
        	list.add(cityName);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
    }
	
	public static String updateLocation(Location location){
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
			String sql = "UPDATE  `cadd`.`location` "
					+ "SET  `storageName` =  ?, `price` =  ? "
					+ "WHERE  `location`.`cityName` =  ?;";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, location.getStorageName());
			stmt.setDouble(2, location.getPrice());
			stmt.setString(3, location.getCityName());
		
			stmt.execute();
			return "";
		} catch (SQLException e){
			return e.getErrorCode() + " " + e.getMessage();
		}
	}
    
}