package main.java.com.joshuashields;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Josh Shields
 */
public  class DatabaseConnection {
    static Connection c = null;
    static Statement s = null;
	static String line;
	static String[] fields;

	public static void establishConnection(){
		try{
			// Get Database Driver, Database, Connection and Statement objects.
            // TODO use a build tool to pull the sqlite dependency
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db/monsters.db");
			s = c.createStatement();
		}
		//Catch exceptions if they occur.
		catch (ClassNotFoundException e ) {
	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
	    }
		catch (SQLException e) {
			System.out.println("SQLException.");
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}

	public static void close(){
		//close() method called in order to disconnect the database before the program finishes.
		try{
			//close connections when done
			s.close();
			c.close();
		}
		//Catch exception if it occurs.
		catch(SQLException e){
			System.out.println("Problem with closing the connection to the database.");
		}
	}
}
