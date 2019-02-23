package com.surenderthakran.indextracker;

import com.google.common.flogger.FluentLogger;

public class Server {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public static void main(String[] args) {
    logger.atInfo().log("Hello World!");
  }
}
