/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Jonny
 */
public class SimpleClient
{
  /*public static void main(String[] args)
  {
    /*try
    {
      final InetAddress ipAddress = 
      //        InetAddress.getByName("www.htl-kaindorf.ac.at");
                InetAddress.getByName("www.google.at");
      System.out.println("IP-Adresse: "+ipAddress.getHostAddress());
      
      final InetAddress ipAddress2 = 
              InetAddress.getByName("172.217.16.163");
      System.out.println(ipAddress2.getHostName());
      
      Socket socket = new Socket(ipAddress,80);
      System.out.println("GEWONNEN!!");
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }*/
  public static String sendRequestAndReceiveResponse(
    String url, int port, String request)
            throws Exception
    {
        final InetAddress ipAddress = InetAddress.getByName(url);
        try(final Socket socket = new Socket(ipAddress, port))
        {
           final BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(socket.getOutputStream(), "utf8"));
           writer.write(request);
           writer.newLine();
           writer.newLine();
           writer.flush();
           
           final BufferedReader reader = new BufferedReader(
            new InputStreamReader (socket.getInputStream(), "utf8"));
           StringBuilder answer = new StringBuilder();
           String zeile = null;
           while ((zeile = reader.readLine()) != null)
           answer.append(zeile).append("\n");     
          return answer.toString(); 
        }
        
    }
    public static void main(String[] args)
    {
        try
        {
           Scanner s = new Scanner(System.in);
           String req = s.nextLine();
            final String answer =
                sendRequestAndReceiveResponse("127.0.0.1",8000,req);
             System.out.println("Antwort:");
             System.out.println(answer);
             s.close();
        }
        catch (Exception ex)
        {
           ex.printStackTrace();
        }
    }
}

