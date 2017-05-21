package com.joshuashields.monsterdatabase;

import java.io.File;
import java.io.FileNotFoundException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

import java.util.Scanner;

/**
 * @author Josh Shields
 */
public class LoadMachine extends DatabaseConnection{
    private PreparedStatement ps;
    private String name;
    private int strength;
    private int defense;
    private int speed;
    private boolean honor;
    private Date dateSlain;

    //No args constructor to instantiate an instance of this object.
    public LoadMachine(){
    }

    //Method to load rows of properly formatted data from the source .CSV file of monsters into the database table.
    public void loadCsv() {
        try{
            //Bring the data in from the file. Create a table with SQL to store data if it doesn't already exist.
            Scanner fromFile = new Scanner(new File("monsters.csv"));
            String sql1 = "create table if not exists SlainMonsters("+
                "ID integer not null primary key,"+
                "Name varchar(30),"+
                "Strength int,"+
                "Defense int," +
                "Speed int," +
                "Honorable_Victory boolean," +
                "Date_Slain date);";
            st.executeUpdate(sql1);
            System.out.println(sql1);
            while (fromFile.hasNextLine( )) {
                //insert each line of data from the file as a row in the database with all of the columns set accordingly.
                line = fromFile.nextLine();
                fields = line.split(" ");
                //id is a primary key and will auto increment
                name = fields[0].trim();
                strength = Integer.parseInt(fields[1].trim());
                defense = Integer.parseInt(fields[2].trim());
                speed = Integer.parseInt(fields[3].trim());
                honor = Boolean.parseBoolean(fields[4].trim());
                //Failsafe on date.
                try{
                    dateSlain = Date.valueOf(fields[5].trim());
                }
                catch (IllegalArgumentException e){
                    dateSlain = new java.sql.Date(new java.util.Date().getTime());
                }
                finally {
                    //With SQL, insert the row to the database's table now that the values have been gathered.
                    String sql2 = "insert into SlainMonsters (Name, Strength, Defense, Speed, Honorable_Victory, Date_Slain)"+
                            String.format("values ('%s', '%d', '%d', '%d', '%b', '%s');", name, strength, defense, speed, honor, dateSlain);
                    ps = con.prepareStatement(sql2);
                    ps.executeUpdate();
                    System.out.println(sql2);
                }
            }
            //Close the PreparedStatement and CSV file for cleanup.
            ps.close();
            fromFile.close();
        }
        //Catch exceptions and print errors for debugging/alerting.
        catch (FileNotFoundException e) {
            System.out.println("File queries.sql not found.");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        catch (SQLException e) {
            System.out.println("SQLException.");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
