package com.surenderthakran.indextracker;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.common.flogger.FluentLogger;

class Connection implements Runnable {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private Socket clientSocket;
  private BufferedReader in;
  private PrintWriter out;
	private BufferedOutputStream dataOut;

  Connection(Socket clientSkt) {
    clientSocket = clientSkt;
  }

  public void run() {
    logger.atInfo().log("Handling new connection");
    try {
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      out = new PrintWriter(clientSocket.getOutputStream());
			dataOut = new BufferedOutputStream(clientSocket.getOutputStream());
    } catch(IOException ioe) {
      logger.atSevere().withCause(ioe).log("Something went wrong while handling connection");
    }
  }
}
