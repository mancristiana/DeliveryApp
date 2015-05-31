package dk.kea.swc.cadd.delivery.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dk.kea.swc.cadd.delivery.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderDAO {

	private final Connection con;
	
	public OrderDAO() {
		con = DBConnector.getConnection();
	}
	
	public ObservableList<Order> getOrders(boolean hasRoute) {
		ObservableList<Order> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT  `order_id` ,  `order`.`cityname` ,  `location`.`storagename` AS  `storagename` ,  `route_id` ,  `quantity` "
            		+ "FROM  `order` ,  `location` "
            		+ "WHERE  `location`.`cityname` =  `order`.`cityname` ";
            if(hasRoute)
            	sql += "AND route_id > 0";
            else sql += "AND route_id = 0";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
            	Integer orderID 	= rs.getInt("order_id");
            	String cityname 	= rs.getString("cityname");
            	String storagename 	= rs.getString("storagename");
            	Integer routeID 	= rs.getInt("route_id");
            	Double quantity 	= rs.getDouble("quantity");

            	list.add(new Order(orderID, cityname, storagename, routeID, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
	
	public String createOrder(Order order) {
        try {
            String sql 	= "INSERT INTO `cadd`.`order` (`cityname`, `route_id`, `quantity`) "
            			+ "VALUES (?, 0, ?);";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, order.getCityName());
            stmt.setDouble(2, order.getQuantity());
            stmt.execute();
            return null;
        } catch (SQLException e) {
            return e.getErrorCode() + " " + e.getMessage();
        }
    }
	
	public String removeOrder(Order order) {
        try {
            String sql 	= "DELETE FROM `cadd`.`order` WHERE `order`.`order_id` = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, order.getOrderID());
            stmt.execute();
            return null;
        } catch (SQLException e) {
            return e.getErrorCode() + " " + e.getMessage();
        }
    }
	
	public String updateOrder(Order order) {
        try {
            String sql 	= "UPDATE  `cadd`.`order` "
            			+ "SET  `cityname` =  ?,"
            			+ "`route_id` =  ?,"
            			+ "`quantity` =  ? "
            			+ "WHERE  `order`.`order_id` =?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, order.getCityName());
            stmt.setInt(2, order.getRouteID());
            stmt.setDouble(3, order.getQuantity());
            stmt.setInt(4, order.getOrderID());
            stmt.execute();
            return null;
        } catch (SQLException e) {
            return e.getErrorCode() + " " + e.getMessage();
        }
    }
	
	public ObservableList<Order> getOrdersByRoute(int routeID) {
		ObservableList<Order> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT  `order_id` ,  `order`.`cityname` ,  `location`.`storagename` AS  `storagename` ,  `route_id` ,  `quantity` "
            		+ "FROM  `order` ,  `location` "
            		+ "WHERE  `location`.`cityname` =  `order`.`cityname` ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, routeID);
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
            	Integer orderID 	= rs.getInt("order_id");
            	String cityname 	= rs.getString("cityname");
            	String storagename 	= rs.getString("storagename");
            	Double quantity 	= rs.getDouble("quantity");

            	list.add(new Order(orderID, cityname, storagename, routeID, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
	
	
//	
//	SELECT * 
//	FROM  `order` 
//	WHERE route_id =1
    
}