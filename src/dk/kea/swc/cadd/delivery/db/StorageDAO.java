package dk.kea.swc.cadd.delivery.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dk.kea.swc.cadd.delivery.model.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StorageDAO {
	
	private final Connection con;
	
	public StorageDAO(){
		con = DBConnector.getConnection();
	}
	
	public ObservableList<Storage> getStorages(){
		ObservableList<Storage> list = FXCollections.observableArrayList();
		try {
			
			String sql = "SELECT * FROM `storage` WHERE 1";
			PreparedStatement stmt = con.prepareStatement(sql); 
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
            	Integer storageID = rs.getInt("storage_id");
            	String cityName = rs.getString("cityname");
            	Integer availableQuantity = rs.getInt("available_quantity");
            	
            	list.add(new Storage(storageID, cityName, availableQuantity));
            	}
            } catch (SQLException e){
            	e.printStackTrace();
            }
		return list;
	}
	
	public String createStorage (Storage storage){
		try{
			String sql = "INSERT INTO storage (storage_id, cityname, available_quantity)"
					   + "VALUES (?, 0, ?);";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, storage.getStorageID());
			stmt.setString(2, storage.getCityName());
			stmt.setInt(3, storage.getAvailableQuantity());
			stmt.execute();
			return null;
		} catch (SQLException e){
			return e.getErrorCode()+ " " + e.getMessage();
		} 
	}
	
	public String removeStorage (Storage storage){
		try{
			String sql = "DELETE FROM storage WHERE order.order_id = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, storage.getStorageID());
			stmt.execute();
			return null;
		}catch (SQLException e){
			return e.getErrorCode() + " " + e.getMessage();
		}	
	}
	public String updateStorage(Storage storage){
		try {
			String sql = "UPDATE storage"
					   + "SET storage_id = ?"
					   + "cityname = ?"
					   + "available_quantity = ?"
					   + "WHERE storage.storage_id = ?;";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, storage.getStorageID());
			stmt.setString(2, storage.getCityName());
			stmt.setInt(3, storage.getAvailableQuantity());
			stmt.execute();
			return null;
		} catch (SQLException e){
			return e.getErrorCode()+ " " + e.getMessage();
		}
	}
}
