/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_test;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Niklas
 */
public abstract class SimpleServer
{
  private final int port;
  private ServerSocket serverSocket = null;
  private HandleRequestThread handleRequestThread = null;
  private final ExecutorService exe = 
          Executors.newWorkStealingPool();

  public SimpleServer(int port)
  {
    this.port = port;
  }
  
  private Socket listen() throws IOException
  {
    return serverSocket.accept();
  }
  
  private String readRequest(Socket socket) throws IOException
  {
    final BufferedReader reader = new BufferedReader(
      new InputStreamReader(socket.getInputStream(), "utf8"));
    final String req = reader.readLine();
    System.out.println(req);
    return req;
  }
  
  protected abstract String createResponse(String request);
  /*{
    return "Du hast gesendet: "+request;
  }
  */
  private void sendResponse(Socket socket, String response) 
          throws IOException
  {
    final BufferedWriter writer = new BufferedWriter(
      new OutputStreamWriter(socket.getOutputStream(), "utf8"));
    writer.write(response);
    writer.newLine();
    writer.flush();
    socket.shutdownOutput();
  }
  
  private void handleRequest() throws IOException
  {
    final Socket socket = listen();
    exe.submit(
    new Runnable()
    {
      @Override
      public void run()
      {
        try
        {
          final String 
              request = readRequest(socket), 
              response = createResponse(request);
          sendResponse(socket, response);
        }
        catch(Exception ignore){}
      }
    });
  }
  
  public void start() throws IOException
  {
    if(serverSocket==null)
    {
      serverSocket = new ServerSocket(port);
      handleRequestThread = new HandleRequestThread();
      handleRequestThread.start();
    }
  }
  
  public void stop() throws IOException
  {
    if(serverSocket!=null)
    {
      handleRequestThread.interrupt();
      serverSocket.close();
      serverSocket=null;
    }
  }

private class HandleRequestThread extends Thread
  {
    @Override
    public void run()
    {
      while(!isInterrupted())
        try        
        {
          handleRequest();
        }
      catch(Exception ignore){}
    } 
  }
  /*
  public static void main(String[] args)
  {
    try
    {
      final SimpleServer server = new SimpleServer(4711);
      server.start();
      Thread.sleep(6000);
      server.stop();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }*/
}
