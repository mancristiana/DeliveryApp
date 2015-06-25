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

	private Connection con;
	
	public DriverDAO() {
		con = DBConnector.getConnection();
	}
	
	public static ObservableList<Driver> getDrivers() {
		ObservableList<Driver> list = FXCollections.observableArrayList();
		Connection connection = null;
		   try {
			connection= DBConnector.getConnection();
			
            String sql = "SELECT * FROM driver ORDER BY driver_id";
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
	
	public Driver getDriverByID(int id) {
		try {
            String sql = "SELECT * FROM driver WHERE driver_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
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
        return null;
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