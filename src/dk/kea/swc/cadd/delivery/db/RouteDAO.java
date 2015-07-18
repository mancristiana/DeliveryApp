package dk.kea.swc.cadd.delivery.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.mysql.jdbc.Statement;

import dk.kea.swc.cadd.delivery.model.Driver;
import dk.kea.swc.cadd.delivery.model.Route;
import dk.kea.swc.cadd.delivery.model.Truck;

public class RouteDAO {
	
	public static ObservableList<Route> getRoutes(boolean isFinished) {
		ObservableList<Route> list = FXCollections.observableArrayList();
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
            String sql = "SELECT  "
            		+ "`route`.`route_id` "
            		+ ",`route`.`date` "
            		+ ", `driver`.`driver_id`,  `driver`.`name`,  `driver`.`phone`,  `driver`.`email`,  `driver`.`available` "
            		+ ",`route`.`truck_id` "
            		+ ",`route`.`finished`"
            		+ ", SUM(  `location`.`price` * `order`.`quantity` ) AS  `totalProfit` "
            		
            		+ "FROM  `route` ,  `order` ,  `location`, `driver` "
            		+ "WHERE  `location`.`cityname` =  `order`.`cityname` "
            		+ "AND `route`.`route_id` = `order`.`route_id`  "
            		+ "AND `route`.`driver_id` = `driver`.`driver_id` "
            		+ "AND finished = ? "
            		+ "GROUP BY  `order`.`route_id`";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setBoolean(1, isFinished);
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
            	Integer routeID 	= rs.getInt("route_id");
            	LocalDate	date 	= LocalDate.now();
            	try {
            		date	= LocalDate.parse(rs.getDate("date").toString());
            	} catch(NullPointerException e) {
            		System.out.println("Date is null and has been set to today's date");
            	}
            	Integer driverID 	= rs.getInt("driver_id");
            	String name 		= rs.getString("name");
            	String phone		= rs.getString("phone");
            	String email 		= rs.getString("email");
            	Boolean available 	= rs.getBoolean("available");
            	Driver driver		= new Driver(driverID,name,phone,email,available);
            	
            	String 	truckID 	= rs.getString("truck_id");
            	Boolean finished 	= rs.getBoolean("finished");
            	Double 	totalProfit = rs.getDouble("totalProfit");

            	list.add(new Route(routeID, date, driver, truckID, finished, totalProfit));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
	
	public static ObservableList<Object> getDriverAndTruck() {
		ObservableList<Object> list = FXCollections.observableArrayList();
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
            String sql = "SELECT * FROM driver , truck WHERE driver.available = 1 AND truck.available = 1 LIMIT 1";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
            	Integer driverId 	= rs.getInt("driver_id");
            	String name 		= rs.getString("name");
            	String phone 		= rs.getString("phone");
            	String email 		= rs.getString("email");
            	String truckId 	= rs.getString("truck_id");
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
	
	/**
	 * Generates a new route in the database connection containing given parameters. 
	 * DB returns an auto-incremented key representing routeId.
	 * Creates a route object connection containing driverId, truckId and DB-returned routeID
	 * @param driver  driver assigned for new route
	 * @param truck   truck assigned for new route
	 * @return created route
	 */
   public static Route createRoute(Driver driver, Truck truck) {
	   Route route = null;
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
		   String sql 	= "INSERT INTO `cadd`.`route` (`date`,`driver_id`, `truck_id`, `finished`) "
   			+ "VALUES (?, ?, ?, 0);";
		   
		   PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		   stmt.setDate(1, Date.valueOf(LocalDate.now()));
		   stmt.setInt(2, driver.getDriverId());
		   stmt.setString(3, truck.getTruckID());
		   
		   stmt.execute();
		   ResultSet rs = stmt.getGeneratedKeys();
		   if(rs.next()){
			   route = new Route(rs.getInt(1), driver, truck.getTruckID(), false);
		   }
	   }catch (SQLException e) {
		   e.printStackTrace();
	   }
	   return route;
   }
   
   public static String updateRoute(Route route) {
	   Connection connection = null;
		try {
			connection= DBConnector.getConnection();
           String sql 	= "UPDATE  `cadd`.`route` "
           				+ "SET  `driver_id` =  ?,"
           				+ "`truck_id` =  ?,"
           				+ "`finished` = ?,"
           				+ "`date` =  ? "
           				+ "WHERE  `route`.`route_id` = ?;";
           PreparedStatement stmt = connection.prepareStatement(sql);
           stmt.setInt(1, route.getDriver().getDriverId());
           stmt.setString(2, route.getTruckID());
           stmt.setBoolean(3, route.isFinished()); 
           stmt.setDate(4, Date.valueOf(route.getDate())); 
           stmt.setInt(5, route.getRouteID());
           stmt.execute();
           return "";
       } catch (SQLException e) {
           return e.getErrorCode() + " " + e.getMessage();
       }
   }
   
   public static String finishRoute(Route route) {
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
           String sql 	= "UPDATE  `cadd`.`route`, `cadd`.`truck`, `cadd`.`driver` "
           		+ "SET  `route`.`finished` = 1, `truck`.`available` = 1, `driver`.`available` = 1 "
           		+ "WHERE  `route`.`route_id` = ? AND `truck`.`truck_id` = ? AND `driver`.`driver_id` = ?;";
           PreparedStatement stmt = connection.prepareStatement(sql);
           stmt.setInt(1, route.getRouteID());
           stmt.setString(2, route.getTruckID());
           stmt.setInt(3, route.getDriver().getDriverId());
           stmt.execute();
           return "";
       } catch (SQLException e) {
    	   e.printStackTrace();
           return e.getErrorCode() + " " + e.getMessage();
       }
   }
   
   public static String deleteRoute(Route route) {
		Connection connection = null;
		try {
			connection= DBConnector.getConnection();
          String sql 	= "DELETE FROM `cadd`.`route` "
          				+ "WHERE  `route`.`route_id` = ?";
          PreparedStatement stmt = connection.prepareStatement(sql);
          stmt.setInt(1, route.getRouteID());
          stmt.execute();
          return "";
      } catch (SQLException e) {
   	   e.printStackTrace();
          return e.getErrorCode() + " " + e.getMessage();
      }
  }
    
}