package java_test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Loggger {
	private final String filename;
	private BufferedWriter writer;
	
	public Loggger(String filename)
	{
		this.filename = filename;
		try {
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void start() throws IOException {
		writer = new BufferedWriter(new FileWriter(filename));
	}
	
	public void logInfo(String info)
	{
		try {
			logText("INFORMATION: "+info);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logError(String error)
	{
		try {
			logText("ERROR: "+error);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void logText(String text) throws IOException
	{
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String timestamp = sdf.format(cal.getTime());
		writer.write(timestamp+" ; "+text+System.getProperty("line.separator"));
		writer.flush();
	}
	
	public void close() {
		try {
			if(writer != null)
				writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
