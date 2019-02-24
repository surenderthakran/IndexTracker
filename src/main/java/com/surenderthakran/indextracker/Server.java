package com.surenderthakran.indextracker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.common.flogger.FluentLogger;

public class Server{
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public static void main(String[] args) {
    try {
      ServerSocket serverSocket = new ServerSocket(18990);
      logger.atInfo().log("Listening on %d...", 18990);

      while (true) {
        Socket clientSocket = serverSocket.accept();
        logger.atInfo().log("New connection created");

        Connection connection = new Connection(clientSocket);

        Thread thread = new Thread(connection);
				thread.start();
      }
    } catch (IOException ioe) {
      logger.atSevere().withCause(ioe).log("Unable to start server.");
    }
  }
}
