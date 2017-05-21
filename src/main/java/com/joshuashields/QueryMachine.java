package main.java.com.joshuashields;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author Josh Shields
 */
public class QueryMachine extends DatabaseConnection{
	private PreparedStatement ps;
	private boolean results;
	
	//No args constructor to instantiate an instance of this object.
	public QueryMachine() {
	}
	
	//Method to display an individual monster based on primary key.
	public void auditMonster(int theID) {
		try{
			//Select the monster where its ID matches the input ID. (Can only ever be 1 since ID is a Primary Key).
			//* = ID, Name, Strength, Defense, Speed, Honorable_Victory, Date_Slain
			String sql =
	    			"SELECT * " +
					"FROM SlainMonsters " +
					"WHERE ID = '"+ theID +"'";
			ps = c.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        System.out.println(sql);
	        //Assume there are no results unless we can get inside the ResultSet from the query.
	        //This way we can notify the user if the ID can't be found.
	        results = false;
		    while (rs.next()) {
		    	//If we get inside the while loop, we know there are results and don't need to print an error message.
		        results = true;
		    	//Get each column of monster data to print.
		        //Here I use column numbers rather than names as a demonstration.
		        System.out.println("ID: " + rs.getInt(1));
		        //Since an int can't be null, we have to check if the ID returned 0 to see if there were no results.
		        System.out.println("Name: " + rs.getString(2));
		        System.out.println("Strength: " + rs.getInt(3));
		        System.out.println("Defense: " + rs.getInt(4));
		        System.out.println("Speed: " + rs.getInt(5));
		        System.out.println("Honorable Victory: " + rs.getString(6));
		        System.out.println("Date Slain: " + rs.getString(7));
		    }
		    //If there are no results, notify the user.
		    if (!results){
	        	System.out.println("\nSorry, it doesn't look like there is a monster with that ID...");
		    }
		    //close the ResultSet and PreparedStatement for cleanup.
		    rs.close();
		    ps.close();
	    }
		//Catch exception and print error/alert message if one occurs.
		catch (SQLException e ) {
	    	System.out.println("SQLException.");
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
	    }
	}
	
	//Method used for showing all of the monsters in the table.
	//Happens automatically when the program starts.
	public void auditAllMonsters() {
		try {
			//Select all monsters, execute the SQL.
			//*=ID, Name, Strength, Defense, Speed, Honorable_Victory, Date_Slain
	    	String sql =
	    			"SELECT * " + 
					"FROM SlainMonsters;";
	    	ps = c.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        System.out.println(sql);
	        //For each row representing a monster, print that data in a defined block.
	        while (rs.next()) {
	        	System.out.println("\n--- Monster Info "+ rs.getInt("ID") +" ---\n");
	            System.out.println("ID: "+rs.getInt("ID"));
	            System.out.println("Name: "+rs.getString("Name"));
	            System.out.println("Strength: "+rs.getInt("Strength"));
	            System.out.println("Defense: "+rs.getInt("Defense"));
	            System.out.println("Speed: "+rs.getInt("Speed"));
	            System.out.println("Honorable Victory: "+rs.getString("Honorable_Victory"));
	            System.out.println("Date Slain: "+rs.getString("Date_Slain"));
	            System.out.println("\n--- End Monster Info ---\n");
	        }
	        //close the ResultSet and PreparedStatement for cleanup.
	        rs.close();
	        ps.close();
	    }
		//Catch exception and print error/alert message if one occurs.
		catch (SQLException e ) {
	    	System.out.println("SQLException.");
    		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    		System.exit(0);
	    }
	}
	
	//Method to query the table on info regarding monsters slain via an honorable victory.
	public void auditHonorableVictories() {
		try {
			//Select labels "true" and "false" and give each label a count of how many times that
			//value has occurred on an Honorable_Victory value.
			String sql =
	    			"SELECT Honorable_Victory, COUNT(Honorable_Victory) " + 
					"FROM SlainMonsters " +
					"GROUP BY Honorable_Victory;";
			ps = c.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        System.out.println(sql);
	        while (rs.next()) {
	        	//Print the results, which should be 2 rows (one for true and one for false).
	        	System.out.println("\nNumber of "+rs.getString(1) + " honorable victories is " + rs.getString(2));
	        }
	        //close the ResultSet and PreparedStatement for cleanup.
	        rs.close();
	        ps.close();
        }
		//Catch exception and print error/alert message if one occurs.
        catch (SQLException e ) {
	    	System.out.println("SQLException.");
    		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    		System.exit(0);
	    }
	}
}