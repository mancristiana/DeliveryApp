package dk.kea.swc.cadd.delivery.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import dk.kea.swc.cadd.delivery.model.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DriverDAO {

	public static ObservableList<Driver> getDrivers() {
		ObservableList<Driver> list = FXCollections.observableArrayList();
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
			
            String sql = "SELECT * FROM driver WHERE retired = 0 ORDER BY driver_id";
            PreparedStatement stmt = connection.prepareStatement(sql);
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
	
	public static Driver getDriverByID(int id) {
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
			
            String sql = "SELECT * FROM driver WHERE driver_id = ? ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
            	Integer driverId 	= rs.getInt("driver_id");
            	String name 		= rs.getString("name");
            	String phone		= rs.getString("phone");
            	String email 		= rs.getString("email");
            	Boolean available 	= rs.getBoolean("available");
            	return new Driver(driverId,name,phone,email,available);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; //TODO sql handling
    }
	
	public static String removeDriver(Driver driver) {
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
			   
			String sql = "DELETE FROM `cadd`.`driver` WHERE `driver`.`driver_id` = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, driver.getDriverId());
		 	stmt.execute();
			return "";
		} catch (SQLException e){
			if(e.getErrorCode() == 1451) { 	// This case is when "Cannot delete or update a parent row: a foreign key constraint fails" 
				try {						// which means driver is assigned to a route
					String sql = "UPDATE  `cadd`.`driver` "
					   		+ "SET  `retired` =  1, `available` = 0 "
					   		+ "WHERE  `driver`.`driver_id` = ?;";
					
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setInt(1, driver.getDriverId());
					stmt.execute();
					return "Driver has been active on routes. \nCan't delete from DB. \nIt is marked as retired instead.";
				} catch (SQLException e2) {
					return "Error code: " +e2.getErrorCode() + "\nMessage: " + e2.getMessage();
				}
				
			}
			return "Error code: " +e.getErrorCode() + "\nMessage: " + e.getMessage();
		}
	}
	
	public static String createDriver(Driver driver) {
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
			   
			String sql = "INSERT INTO `cadd`.`driver` (`driver_id`, `name`, `phone`, `email`, `available`) VALUES (NULL, ?, ?, ?, ?);";
			   
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, driver.getName());
			stmt.setString(2, driver.getPhone());
			stmt.setString(3, driver.getEmail());
			stmt.setBoolean(4, driver.getAvailable());
			stmt.execute();
			   
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next()){
				driver.setDriverId(rs.getInt(1));
			}
			return "";
		} catch (SQLException e) {
			return "Error code: " +e.getErrorCode() + "\nMessage: " + e.getMessage();
		}
	}
	
	public static String updateDriver(Driver driver){
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
			
			String sql = "UPDATE  `cadd`.`driver` "
			   		+ "SET  `name` =  ?,"
			   		+ "`phone` =  ?,"
			   		+ "`email` =  ?,"
			   		+ "`available` = ? "
			   		+ "WHERE  `driver`.`driver_id` = ?;";
			
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, driver.getName());
			stmt.setString(2, driver.getPhone());
			stmt.setString(3, driver.getEmail());
			stmt.setBoolean(4, driver.getAvailable());
			stmt.setInt(5, driver.getDriverId());
			stmt.execute();
			return "";
		} catch (SQLException e) {
			return "Error code: " +e.getErrorCode() + "\nMessage: " + e.getMessage();
		}
	}
}