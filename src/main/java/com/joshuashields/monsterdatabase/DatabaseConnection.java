package com.joshuashields.monsterdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Josh Shields
 */
public class DatabaseConnection {
    static Connection con = null;
    static Statement st = null;
    static String line;
    static String[] fields;

    public static void establishConnection() {
        try {
            // Get Database Driver, Database, Connection and Statement objects.
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:db/monsters.db");
            st = con.createStatement();
        }
        //Catch exceptions if they occur.
        catch (ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void close() {
        //close() method called in order to disconnect the database before the program finishes.
        try {
            //close connections when done
            st.close();
            con.close();
        }
        //Catch exception if it occurs.
        catch(SQLException e){
            System.out.println("Problem with closing the connection to the database.");
        }
    }
}
