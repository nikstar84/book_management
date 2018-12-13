package java_test;


import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

public class Server extends SimpleServer {
	
	private final DataBaseConnection con;
	private final Loggger logger;

	public Server(int port){
		super(port);
		con = new DataBaseConnection("jdbc:sqlite:/home/niklas/db/book_database.db");
		logger = new Loggger("/home/niklas/book_management/java_test/src/test.log");
		logger.logInfo("Server started");
	}
	
	public void closeDB() throws Exception {
		con.disconnect();
		logger.logInfo(" Server disconnected");
		logger.close();
	}

	@Override
	protected String createResponse(String request) {
		logger.logInfo(" Incoming Request: "+request);
		try {
			if(request.startsWith("CHECK"))
				return "OK\n";
			else if(request.startsWith("{"))
			{
				return putIntoDB(request);
			}
			else if(request.startsWith("GET"))
			{
				return getData(request);
			}
		} catch (Exception e) {
			logger.logError(e.getMessage());
		}
		return "UNKWOWNCOMMAND";
	}
	
	// example protocol: GET;BOOKBYID;4 //returns the 4th book in the database

	private String getData(String request) throws Exception {
		String [] t = request.split(";");
		switch (t[1]) {
		case "BOOKBYID":
			return con.getBookByIsbn(t[2]).toJson().toString();
		case "BOOKBYTITLE":
			return con.getBookByTitle(t[2]).toJson().toString();
		case "BOOKSBYAUTHOR":
			List<Book> books = con.getBooksByAuthor(t[2]);
			StringBuilder b = new StringBuilder();
			for(Book book : books) {
				b.append(book.toJson().toString());
				b.append('\n');
			}
			return b.toString();
		default:
			return "WRONG COMMAND";
		}
	}

	private String putIntoDB(String request) throws Exception {
		final Book b = new Book(new JSONObject(request));
		return con.putIntoBD(b);
	}

	public static void main(String[] args) {
		Server server = new Server(8000);
		try {
			server.start();
			while(true)
			{
				;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				server.stop();
				server.closeDB();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
