package dk.kea.swc.cadd.delivery.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dk.kea.swc.cadd.delivery.model.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DriverDAO {

	private final Connection con;
	
	public DriverDAO() {
		con = DBConnector.getConnection();
	}
	
	public ObservableList<Driver> getDrivers() {
		ObservableList<Driver> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM driver ORDER BY driver_id";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
            	Integer driverId 	= rs.getInt("driver_id");
            	String name 		= rs.getString("name");
            	String phone		= rs.getString("phone");
            	String email 		= rs.getString("email");
            	Boolean available 	= rs.getBoolean("available");
            	list.add(new Driver(driverId,name,phone,email,available));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
}