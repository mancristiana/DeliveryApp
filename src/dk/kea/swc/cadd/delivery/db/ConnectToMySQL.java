package dk.kea.swc.cadd.delivery.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectToMySQL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        select("get rich");
    }
    
    public static void select(String paramName) {
        try {
            Connection con = DBConnector.getConnection();
            
            String sql = "SELECT * FROM issue WHERE title = ?;";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, paramName);
            
            stmt.executeQuery();
            
            System.out.println(sql);
            
            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
                int id         = rs.getInt("id");
                String name    = rs.getString("title");

                System.out.println("ID = " + id + " Name = " + name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}