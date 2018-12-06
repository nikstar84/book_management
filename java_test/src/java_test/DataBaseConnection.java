package java_test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DataBaseConnection {
	
	private final String url;
	private Connection conn;
	
	public DataBaseConnection(String url) {
		this.url = url;	
	}
	
	public void connect() {
		try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }		
	}
	
	public String putIntoBD(Book b) {
		final String cmd = "INSERT INTO books VALUES ("
				+ "0,"
				+ "'" + b.getTitle() + "'" +","
				+ "'" + b.getSubtitle() + "'" +","
				+ "'" + b.getAuthors() + "'" +","
				+ "'" + b.getPublisher() + "'" +","
				+ new Integer(b.getPages()).toString() +","
				+ "'" + b.getPublishingDate() + "'" +","
				+ "'" + b.getDescription() + "'" +","
				+ "'" + b.getLanguage() + "'" +","
				+ "'" + b.getImgLink() + "'" +","
				+ "'" + b.getIsbn() + "'"
				+");";
		try {
			System.out.println(cmd);
			executeCommand(cmd);
			return "ALRIGHT";
		} catch (Exception e) {
			return "ERROR: "+ e.getMessage();
		}
	}

	private void executeCommand(String com) throws Exception
	{
		Connection con = DriverManager.getConnection(url);
		Statement stmt = con.createStatement();
        // create a new table
        stmt.execute(com);
	}
	
	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<>();
		return books;
	}
	
	public Book getBookById(int id) throws Exception {
		Book b = new Book(null);
		return b;
	}
	
	public Book getBookByTitle(String title) throws Exception {
		Book b = new Book(null);
		return b;
	}
	
	public List<Book> getBooksByAuthor(String aut) throws Exception {
		List<Book> books = new ArrayList<>();
		return books;
	}
	
	public void updateBook(int id, Book newData) {
		
	}
	
	public void deleteBook(int id) {
		
	}

}
