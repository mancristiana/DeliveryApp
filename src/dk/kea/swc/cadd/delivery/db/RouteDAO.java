package dk.kea.swc.cadd.delivery.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import dk.kea.swc.cadd.delivery.model.Driver;
import dk.kea.swc.cadd.delivery.model.Route;
import dk.kea.swc.cadd.delivery.model.Truck;
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
	
	public ObservableList<Object> getDriverAndTruck() {
		ObservableList<Object> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM driver , truck WHERE driver.available = 1 AND truck.available = 1 LIMIT 1";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
            	Integer driverId 	= rs.getInt("driver_id");
            	String name 		= rs.getString("name");
            	String phone 		= rs.getString("phone");
            	String email 		= rs.getString("email");
            	Integer truckId 	= rs.getInt("truck_id");
            	Integer capacity	= rs.getInt("capacity");
            	Double speed		= rs.getDouble("speed");
            	
            	Driver driver = new Driver(driverId,name,phone,email,true);
            	Truck truck = new Truck(truckId, capacity, speed, true);

            	list.add(driver);
            	list.add(truck);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
	
	   public Route createRoute(Integer driverId, Integer truckId) {
		   Route route = null;
		   try {
			   String sql 	= "INSERT INTO `cadd`.`route` (`driver_id`, `truck_id`, `finished`) "
           			+ "VALUES (?, ?, 0);";
			   
			   PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			   stmt.setInt(1, driverId);
			   stmt.setInt(2, truckId);
			   
			   stmt.execute();
			   ResultSet rs = stmt.getGeneratedKeys();
			   if(rs.next()){
				   route = new Route(rs.getInt(1),driverId,truckId,false);
			   }
		   }catch (SQLException e) {
			   e.printStackTrace();
		   }
		   return route;
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