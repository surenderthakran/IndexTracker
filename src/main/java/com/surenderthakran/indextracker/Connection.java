package com.surenderthakran.indextracker;

import com.google.common.flogger.FluentLogger;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Date;

class Connection implements Runnable {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private Socket clientSocket;
  private BufferedReader in;
  private PrintWriter out;
  private BufferedOutputStream dataOut;
  private Request request;

  Connection(Socket clientSkt) {
    clientSocket = clientSkt;
  }

  @Override
  public void run() {
    logger.atInfo().log("Handling new connection");
    try {
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      out = new PrintWriter(clientSocket.getOutputStream());
      dataOut = new BufferedOutputStream(clientSocket.getOutputStream());

      request = this.readRequest(in);
      System.out.println(request);

      Router.route(request);

      String responseBody = "Hello";

      // send HTTP Headers
      out.println("HTTP/1.1 200 OK");
      out.println("Server: Java HTTP Server : 1.0");
      out.println("Date: " + new Date());
      out.println("Content-type: application/json");
      out.println("Content-length: " + responseBody.length());
      out.println(); // blank line between headers and content, very important !
      out.flush(); // flush character output stream buffer

      dataOut.write(responseBody.getBytes(Charset.forName("UTF-8")));
      dataOut.flush();
    } catch (IOException ioe) {
      logger.atSevere().withCause(ioe).log("Something went wrong while handling connection");
    } finally {
      try {
        in.close();
        out.close();
        dataOut.close();
        clientSocket.close();
      } catch (IOException ioe) {
        logger.atSevere().withCause(ioe).log("Unable to free resources after sending response.");
      }
    }
  }

  private Request readRequest(BufferedReader in) throws IOException {
    String line = in.readLine();
    while (line != null && line.length() > 0) {
      System.out.println(line);
      line = in.readLine();
    }

    return request = new Request().setMethod("GET");
  }
}
