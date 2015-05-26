package dk.kea.swc.cadd.delivery.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dk.kea.swc.cadd.delivery.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderDAO {

	private final Connection con;
	
	public OrderDAO() {
		con = DBConnector.getConnection();
	}
	
	public ObservableList<Order> getOrders() {
		ObservableList<Order> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM `order` WHERE 1";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
            	Integer orderID 	= rs.getInt("order_id");
            	String cityname 	= rs.getString("cityname");
            	Integer routeID 	= rs.getInt("route_id");
            	Double quantity 	= rs.getDouble("quantity");

            	list.add(new Order(orderID, cityname, routeID, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
	
	public String createOrder(Order order) {
        try {
            String sql 	= "INSERT INTO `cadd`.`order` (`cityname`, `route_id`, `quantity`) "
            			+ "VALUES (?, NULL, ?);";
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
    
}