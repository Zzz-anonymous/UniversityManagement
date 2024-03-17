/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Zy
 */
public class Database {
    public static Connection getConnection(){
         Connection con = null;
         try{
             Class.forName("com.mysql.jdbc.Driver");
             con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagement","admin","admin");
         }catch(Exception e){
             System.out.println(e);
         }
         return con;
     }
    
     public static void main(String[] args) {
        try {
            Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            if (rs.next()) {
                System.out.println("Connection to the database successful!");
            } else {
                System.out.println("No data found in the 'students' table.");
            }
            rs.close(); // Close result set
            stmt.close(); // Close statement
            conn.close(); // Close connection
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database.");
        }
    }
}


