package java_test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DataBaseConnection {
	
	private final String url;
	private Connection conn;
	
	public DataBaseConnection(String url) {
		this.url = url;	
		connect();
	}
	
	public void connect() {
		try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }		
	}
	
	public void disconnect() throws SQLException
	{
		conn.close();
	}
	
	public String putIntoBD(Book b) {
		final String cmd = "INSERT INTO books VALUES ("
				+ "0,"
				+ "'" + b.getTitle() + "',"
				+ "'" + b.getSubtitle() + "',"
				+ "'" + b.getAuthors() + "',"
				+ "'" + b.getPublisher() + "',"
				+ new Integer(b.getPages()).toString() +","
				+ "'" + b.getPublishingDate() + "',"
				+ "'" + b.getDescription() + "',"
				+ "'" + b.getLanguage() + "',"
				+ "'" + b.getImgLink() + "',"
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
		//Connection con = DriverManager.getConnection(url);
		Statement stmt = conn.createStatement();
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
		Statement stmt = conn.createStatement();
		final ResultSet rs = stmt.executeQuery("Select * from books where title = '"+title+"';");
		//String res = rs.getString("authors");
		//System.out.println(res.substring(1,res.length()-1).replaceAll("\"", ""));
		Book b = getBookFromRs(rs);
		return b;
	}
	
	private Book getBookFromRs(ResultSet rs) throws Exception {
		String res = rs.getString("authors");
		Book b = new Book(rs.getString("isbn"),
				rs.getString("title"),
				rs.getString("subtitle"), 
				rs.getString("publisher"),
				res.substring(1,res.length()-1).replaceAll("\"", ""),
				rs.getInt("pages"),
				rs.getString("publishingDate"), 
				rs.getString("description"),
				rs.getString("language"),
				rs.getString("imgLink"));
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
