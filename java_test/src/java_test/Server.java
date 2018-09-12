package java_test;

import java.io.IOException;
import java.util.List;

public class Server extends SimpleServer {
	
	private final DataBaseConnection con;

	public Server(int port) {
		super(port);
		con = new DataBaseConnection("");
	}

	@Override
	protected String createResponse(String request) {
		try {
			if(request.startsWith("CHECK"))
				return "OK\n";
			else if(request.startsWith("NEW"))
			{
				return putIntoDB(request);
			}
			else if(request.startsWith("GET"))
			{
				return getData(request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "UNKWOWNCOMMAND";
	}
	
	// example protocol: GET;BOOKBYID;4 //returns the 4th book in the database

	private String getData(String request) throws Exception {
		String [] t = request.split(";");
		switch (t[1]) {
		case "BOOKBYID":
			return con.getBookById(new Integer(t[2])).toJson().toString();
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

	private String putIntoDB(String request) {
		// TODO Auto-generated method stub
		return "";
	}

	public static void main(String[] args) {
		Server server = new Server(8000);
		try {
			server.start();
			System.out.println("Server gestartet");
			Thread.sleep(15000);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
