package dk.kea.swc.cadd.delivery.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dk.kea.swc.cadd.delivery.model.Location;
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
            	Double price 		= rs.getDouble("price");
            	Double latitude 	= rs.getDouble("latitude");
            	Double longitude 	= rs.getDouble("longitude");
            	list.add(new Location(cityName,price,latitude,longitude));
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
            	Double price 		= rs.getDouble("price");
            	Double latitude 	= rs.getDouble("latitude");
            	Double longitude 	= rs.getDouble("longitude");
            	return new Location(cityName,price,latitude,longitude);
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
    
}