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

	public void insertBook(Book b) {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
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
