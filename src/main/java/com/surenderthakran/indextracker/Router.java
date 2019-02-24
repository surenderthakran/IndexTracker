package com.surenderthakran.indextracker;

import com.google.common.flogger.FluentLogger;
import com.surenderthakran.indextracker.handlers.GetStockHandler;

class Router {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  static void route(Request request) {
    logger.atInfo().log(request.toString());

    if (request.method() == "GET") {
      System.out.println("Routing GET request");
      GetStockHandler.handle(request);
    }
  }
}
