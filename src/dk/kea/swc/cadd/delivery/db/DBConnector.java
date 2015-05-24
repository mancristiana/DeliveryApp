package dk.kea.swc.cadd.delivery.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    
    private static final String USER = "cadd";
    private static final String PASS = "teamrocket";
    private static final String DB = "cadd";
    private static final String URL = "jdbc:mysql://138.128.216.12";
    private static final String PORT = "3306";
    
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String urlForConn = URL + ":" + PORT + "/" + DB;
            con = DriverManager.getConnection(urlForConn, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
        	e.printStackTrace();
        }
        return con;
    }
    
}