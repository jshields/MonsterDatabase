package main.java.com.joshuashields;

import java.util.Scanner;
/**
 * @author Josh Shields
 */
public class Driver {
    //This class is to drive the objects and methods of the application when run.
    //It also interfaces with the user.
    private static Scanner in;
    static private String command;
    private static LoadMachine load;
    private static QueryMachine query;

    /**
     * @param args
     */
    public static void main(String[] args) {
        //Open a connection to the database 
        DatabaseConnection.establishConnection();
        //Instantiate the necessary objects to create the database and query it,
        //and call the method to insert the .CSV data.
        load = new LoadMachine();
        query = new QueryMachine();
        load.loadCsv();
        //Display all of the monsters in the database,
        //and display a report on the cases of an honorable victory.
        query.auditAllMonsters();
        query.auditHonorableVictories();
        //Create a scanner to gather input from the user's keyboard [used in CheckInput()].
        in = new Scanner(System.in);
        //Call the method take input from the user's keyboard to look up a monster by their primary key.
        checkInput();
    }

    public static void checkInput() {
        //Look at the input from the user's keyboard,
        //to query a monster from the database table by their primary key.
        //If they typed "exit" then we will exit once the system sees that.
        command = null;
        System.out.println("\nEnter the monster ID you would like to find (or \"exit\" to quit): ");
        command = in.nextLine();
        //Convert input to lower case so that the "exit" command is not case sensitive.
        command = command.toLowerCase();
        try {
            //If the command can be parsed as an int, then audit for an ID matching that int.
            query.auditMonster(Integer.parseInt(command));
        }
        finally {
            if (command.equals("exit")) {
                //close database connection
                DatabaseConnection.close();
                //close scanner.
                in.close();
                //confirmation of command
                System.out.println("Goodbye.");
                //complete the exit command by exiting the application
                System.exit(0);
            }
            else if (!command.matches("\\d*")) {
                //If the command was not "exit" and does not regex match a digit of any length,
                //it must be invalid. Print a message about it.
                System.out.println("Sorry, command not recogized.");
            }
            //Recursively check input, because the user hasn't entered "exit" yet.
            checkInput();
        }
    }
}
