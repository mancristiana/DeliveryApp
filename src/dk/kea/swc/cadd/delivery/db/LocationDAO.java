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
		ObservableList<Location> list = FXCollections.observableArrayList();;
        try {
            String sql = "SELECT * FROM location ORDER BY cityname";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
            	Integer locationID 	= rs.getInt("location_id");
            	String cityName 	= rs.getString("cityname");
            	Double price 		= rs.getDouble("price");
            	Double latitude 	= rs.getDouble("latitude");
            	Double longitude 	= rs.getDouble("longitude");
            	list.add(new Location(locationID,cityName,price,latitude,longitude));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
}