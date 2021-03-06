package dk.kea.swc.cadd.delivery.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dk.kea.swc.cadd.delivery.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderDAO {

	public static ObservableList<Order> getOrders(boolean hasRoute) {
		ObservableList<Order> list = FXCollections.observableArrayList();
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
            String sql = "SELECT  `order_id` "
            		+ ", `order`.`cityname` "
            		+ ", `location`.`storagename` AS  `storagename`"
            		+ ", `route_id` "
            		+ ", `quantity` "
            		+ ", `location`.`price` "
            		+ ", `location`.`price` * `order`.`quantity` AS profit "
            		+ "FROM  `order` ,  `location` "
            		+ "WHERE  `location`.`cityname` =  `order`.`cityname` ";
            if(hasRoute)
            	sql += "AND route_id > 0 ";
            else sql += "AND route_id = 0 ";
            sql += "ORDER BY `profit` DESC ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
            	Integer orderID 	= rs.getInt("order_id");
            	String cityname 	= rs.getString("cityname");
            	String storagename 	= rs.getString("storagename");
            	Integer routeID 	= rs.getInt("route_id");
            	Double quantity 	= rs.getDouble("quantity");
            	Double price 		= rs.getDouble("price");
            	Double profit 		= rs.getDouble("profit");

            	list.add(new Order(orderID, cityname, storagename, routeID, quantity, price, profit));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
	
	public static String createOrder(Order order) {
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
            String sql 	= "INSERT INTO `cadd`.`order` (`cityname`, `route_id`, `quantity`) "
            			+ "VALUES (?, 0, ?);";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, order.getCityName());
            stmt.setDouble(2, order.getQuantity());
            stmt.execute();
            return "";
        } catch (SQLException e) {
            return e.getErrorCode() + " " + e.getMessage();
        }
    }
	
	public static String removeOrder(Order order) {
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
            String sql 	= "DELETE FROM `cadd`.`order` WHERE `order`.`order_id` = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, order.getOrderID());
            stmt.execute();
            return "";
        } catch (SQLException e) {
            return e.getErrorCode() + " " + e.getMessage();
        }
    }
	
	public static String updateOrder(Order order) {
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
            String sql 	= "UPDATE  `cadd`.`order` "
            			+ "SET  `cityname` =  ?,"
            			+ "`route_id` =  ?,"
            			+ "`quantity` =  ? "
            			+ "WHERE  `order`.`order_id` =?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, order.getCityName());
            stmt.setInt(2, order.getRouteID());
            stmt.setDouble(3, order.getQuantity());
            stmt.setInt(4, order.getOrderID());
            stmt.execute();
            return "";
        } catch (SQLException e) {
            return e.getErrorCode() + " " + e.getMessage();
        }
    }
	
	public static ObservableList<Order> getOrdersByRoute(int routeID) {
		ObservableList<Order> list = FXCollections.observableArrayList();
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
            String sql = "SELECT  `order_id` ,  `order`.`cityname` ,  `location`.`storagename` AS  `storagename` ,  `route_id` ,  `quantity` "
            		+ ", `location`.`price` "
            		+ ", `location`.`price` * `order`.`quantity` AS profit "
            		+ "FROM  `order` ,  `location` "
            		+ "WHERE  `location`.`cityname` =  `order`.`cityname` AND route_id = ? ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, routeID);
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
            	Integer orderID 	= rs.getInt("order_id");
            	String cityname 	= rs.getString("cityname");
            	String storagename 	= rs.getString("storagename");
            	Double quantity 	= rs.getDouble("quantity");
            	Double price 		= rs.getDouble("price");
            	Double profit 		= rs.getDouble("profit");

            	list.add(new Order(orderID, cityname, storagename, routeID, quantity, price, profit));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
}