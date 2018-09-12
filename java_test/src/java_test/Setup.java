package java_test;

import java.sql.*;

public class Setup {
	
	private final static String url = "jdbc:sqlite:/home/niklas/db/book_database.db";
	
	private static void executeCommand(String com)
	{
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(com);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

	}

	public static void main(String[] args) {
				 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        
        String com2 = "CREATE TABLE books("
    	            +"id integer PRIMARY KEY,"
    	            +"packageNumber integer NOT NULL,"
    	            +"title text NOT NULL,"
    	            +"subtitle text,"
    	            +"authors text,"
    	            +"publisher text,"
    	            +"pages integer,"
    	            +"publishingDate text,"
    	            +"description text,"
    	            +"language text,"
    	            +"imgLink text,"
    	            +"isbn text"+
    	            ");";
    	            
        executeCommand(com2);

	}

}
