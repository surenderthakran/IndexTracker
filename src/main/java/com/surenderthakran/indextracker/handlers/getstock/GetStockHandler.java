package com.surenderthakran.indextracker.handlers;

import com.google.common.flogger.FluentLogger;
import com.surenderthakran.indextracker.net.Request;

public class GetStockHandler {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public static void handle(Request request) {
    logger.atInfo().log("Handling GetStock request");
  }
}
