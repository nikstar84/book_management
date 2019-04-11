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
				+ new Integer(b.getPackageNumber()).toString() +","
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
	
	public Book getBookByIsbn(String id) throws Exception {
		Statement stmt = conn.createStatement();
		final ResultSet rs = stmt.executeQuery("Select * from books where isbn = '"+id+"';");
		Book b = getBookFromRs(rs).get(0);
		return b;
	}
	
	public Book getBookByTitle(String title) throws Exception {
		Statement stmt = conn.createStatement();
		final ResultSet rs = stmt.executeQuery("Select * from books where title like '%"+title+"%';");
		//String res = rs.getString("authors");
		//System.out.println(res.substring(1,res.length()-1).replaceAll("\"", ""));
		Book b = getBookFromRs(rs).get(0);
		return b;
	}
	
	private List<Book> getBookFromRs(ResultSet rs) throws Exception {
		List<Book> books = new ArrayList<>();
		while(rs.next()){
			String res = rs.getString("authors");
			//res.substring(0,res.length()-1).replaceAll("\"", "");
			Book b = new Book(rs.getString("isbn"),
					rs.getString("title"),
					rs.getString("subtitle"), 
					rs.getString("publisher"),
					res.substring(1,res.length()-1).replaceAll("\"", ""),
					rs.getInt("pages"),
					rs.getString("publishingDate"), 
					rs.getString("description"),
					rs.getString("language"),
					rs.getString("imgLink"),
					rs.getInt("packageNumber"));
		
			books.add(b);
		}
		return books;
	}

	public List<Book> getBooksByAuthor(String aut) throws Exception {
		Statement stmt = conn.createStatement();
		final ResultSet rs = stmt.executeQuery("Select * from books where authors like '%"+aut+"%';");
		return getBookFromRs(rs);
	}
	
	public void updateBook(int id, Book newData) {
		
	}
	
	public void deleteBook(int id) {
		
	}

}
