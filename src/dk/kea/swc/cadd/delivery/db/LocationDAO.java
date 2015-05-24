package dk.kea.swc.cadd.delivery.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import dk.kea.swc.cadd.delivery.model.Location;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
            	IntegerProperty locationID 	= new SimpleIntegerProperty(rs.getInt("location_id"));
            	StringProperty cityName 	= new SimpleStringProperty(rs.getString("cityname"));
            	DoubleProperty price 		= new SimpleDoubleProperty(rs.getDouble("price"));
            	StringProperty latitude 	= new SimpleStringProperty(rs.getString("latitude"));
            	StringProperty longitude 	= new SimpleStringProperty(rs.getString("longitude"));
            	list.add(new Location(locationID,cityName,price,latitude,longitude));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
}