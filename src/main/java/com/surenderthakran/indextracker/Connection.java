package com.surenderthakran.indextracker;

import com.google.common.flogger.FluentLogger;
import com.google.common.flogger.StackSize;
import com.surenderthakran.indextracker.net.exceptions.InvalidRequestException;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Connection implements Runnable {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private Socket clientSocket;
  private BufferedReader in;
  private PrintWriter out;
  private BufferedOutputStream dataOut;

  Connection(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  @Override
  public void run() {
    logger.atInfo().log("Handling new connection");
    try {
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      out = new PrintWriter(clientSocket.getOutputStream());
      dataOut = new BufferedOutputStream(clientSocket.getOutputStream());

      Request request = this.parseRequest(in);

      Response response = RequestRunner.execute(request);

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
      logger.atSevere().withCause(ioe).withStackTrace(StackSize.FULL).log(
          "Something went wrong while handling connection.");
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

  private Request parseRequest(BufferedReader in) throws IOException {
    List<String> lines = new ArrayList<>();

    try {
      Request request = new Request();
      String line = in.readLine();
      System.out.println("==============================");
      while (line != null && line.length() > 0) {
        lines.add(line);
        System.out.println(line);
        if (request.isRequestLine(line)) {
          request.readRequestLine(line);
        }

        line = in.readLine();
      }
      System.out.println("==============================");

      return request;
    } catch (InvalidRequestException ire) {
      throw new IOException(
          String.format("Something went wrong while parsing request %s:", lines), ire);
    }
  }
}
