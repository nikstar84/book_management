/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.niklas.bookmanagement.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Niklas
 */
public class SimpleClient
{
  public static String sendRequestAndReceiveResponse(
    String url, int port, String request)
            throws Exception
    {
        final String req = request;
        final int p = port;
        final InetAddress ipAddress = InetAddress.getByName(url);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                try(final Socket socket = new Socket(ipAddress, p))
                {
                    final BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream(), "utf8"));
                    writer.write(req);
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
                } catch (Exception e) {
                    return "keine Verbindung";
                }
            }
        };
        Future<String> future = executor.submit(callable);
        return future.get();
    }
}

