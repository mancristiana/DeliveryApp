package dk.kea.swc.cadd.delivery.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dk.kea.swc.cadd.delivery.model.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StorageDAO {
	
	public static ObservableList<Storage> getStorages(){
		ObservableList<Storage> list = FXCollections.observableArrayList();
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
			
			String sql = "SELECT * FROM `storage` WHERE 1";
			PreparedStatement stmt = connection.prepareStatement(sql); 
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                String cityName = rs.getString("cityname");
            	Double availableQuantity = rs.getDouble("available_quantity");
            	
            	list.add(new Storage(cityName, availableQuantity));
            	}
            } catch (SQLException e){
            	e.printStackTrace();
            }
		return list;
	}
	
	public static String createStorage (Storage storage){
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
			String sql = "INSERT INTO storage (cityname, available_quantity)"
					   + "VALUES (?, ?);";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, storage.getCityName());
			stmt.setDouble(2, storage.getAvailableQuantity());
			stmt.execute();
			return "";
		} catch (SQLException e){
			return e.getErrorCode()+ " " + e.getMessage();
		} 
	}
	
	public static String removeStorage (Storage storage){
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
			String sql = "DELETE FROM storage WHERE cityname = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, storage.getCityName());
			stmt.execute();
			return "";
		}catch (SQLException e){
			return e.getErrorCode() + " " + e.getMessage();
		}	
	}
	public static String updateStorage(Storage storage){
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
			String sql = "UPDATE storage "
					   + "SET available_quantity = ? "
					   + "WHERE cityname = ?;";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setDouble(1, storage.getAvailableQuantity());
			stmt.setString(2, storage.getCityName());
		
			stmt.execute();
			return "";
		} catch (SQLException e){
			return e.getErrorCode() + " " + e.getMessage();
		}
	}
	
	public static ObservableList<String> getStorageNames() {
		ObservableList<String> list = FXCollections.observableArrayList();
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
            String sql = "SELECT cityname FROM storage WHERE 1";
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
}
