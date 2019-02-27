package com.surenderthakran.indextracker;

import com.google.common.flogger.FluentLogger;
import com.surenderthakran.indextracker.net.Server;

class App {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public static void main(String[] args) {
    Server server = new Server();
    server.start();
  }
}
