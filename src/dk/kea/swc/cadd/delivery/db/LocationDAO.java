package dk.kea.swc.cadd.delivery.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dk.kea.swc.cadd.delivery.model.Location;
import dk.kea.swc.cadd.delivery.model.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LocationDAO {

	private final Connection con;
	
	public LocationDAO() {
		con = DBConnector.getConnection();
	}
	
	public ObservableList<Location> getLocations() {
		ObservableList<Location> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM location ORDER BY cityname";
            PreparedStatement stmt = con.prepareStatement(sql);
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
	
	public Location getLocationByID(String id) {
		try {
            String sql = "SELECT * FROM location WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
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
	
	public ObservableList<String> getLocationNames() {
		ObservableList<String> list = FXCollections.observableArrayList();
		try {
            String sql = "SELECT cityname FROM location WHERE 1";
            PreparedStatement stmt = con.prepareStatement(sql);
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
	
	public String updateLocation(Location location){
		try {
			String sql = "UPDATE  `cadd`.`location` "
					+ "SET  `storageName` =  ?, `price` =  ? "
					+ "WHERE  `location`.`cityName` =  ?;";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, location.getStorageName());
			stmt.setDouble(2, location.getPrice());
			stmt.setString(3, location.getCityName());
		
			stmt.execute();
			return null;
		} catch (SQLException e){
			return e.getErrorCode() + " " + e.getMessage();
		}
	}
    
}