package dk.kea.swc.cadd.delivery.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dk.kea.swc.cadd.delivery.model.Route;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RouteDAO {

	private final Connection con;
	
	public RouteDAO() {
		con = DBConnector.getConnection();
	}
	
	public ObservableList<Route> getRoutes(boolean isFinished) {
		ObservableList<Route> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM  `route` WHERE finished = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setBoolean(1, isFinished);
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
            	Integer routeID 	= rs.getInt("route_id");
            	Integer driverID 	= rs.getInt("driver_id");
            	Integer truckID 	= rs.getInt("truck_id");
            	Boolean finished 	= rs.getBoolean("finished");

            	list.add(new Route(routeID, driverID, truckID, finished));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
	
//	public String createRoute(Order order) {
//        try {
//            String sql 	= "INSERT INTO `cadd`.`order` (`cityname`, `route_id`, `quantity`) "
//            			+ "VALUES (?, 0, ?);";
//            PreparedStatement stmt = con.prepareStatement(sql);
//            stmt.setString(1, order.getCityName());
//            stmt.setDouble(2, order.getQuantity());
//            stmt.execute();
//            return null;
//        } catch (SQLException e) {
//            return e.getErrorCode() + " " + e.getMessage();
//        }
//    }
//	
//	public String removeOrder(Order order) {
//        try {
//            String sql 	= "DELETE FROM `cadd`.`order` WHERE `order`.`order_id` = ?";
//            PreparedStatement stmt = con.prepareStatement(sql);
//            stmt.setInt(1, order.getOrderID());
//            stmt.execute();
//            return null;
//        } catch (SQLException e) {
//            return e.getErrorCode() + " " + e.getMessage();
//        }
//    }
//	
//	public String updateOrder(Order order) {
//        try {
//            String sql 	= "UPDATE  `cadd`.`order` "
//            			+ "SET  `cityname` =  ?,"
//            			+ "`route_id` =  ?,"
//            			+ "`quantity` =  ? "
//            			+ "WHERE  `order`.`order_id` =?;";
//            PreparedStatement stmt = con.prepareStatement(sql);
//            stmt.setString(1, order.getCityName());
//            stmt.setInt(2, order.getRouteID());
//            stmt.setDouble(3, order.getQuantity());
//            stmt.setInt(4, order.getOrderID());
//            stmt.execute();
//            return null;
//        } catch (SQLException e) {
//            return e.getErrorCode() + " " + e.getMessage();
//        }
//    }
    
}