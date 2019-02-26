package com.surenderthakran.indextracker;

import com.google.common.flogger.FluentLogger;

class RequestRunner {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  static Response execute(Request request) {
    logger.atInfo().log(request.toString());

    // if (request.method() == "GET") {
    //   System.out.println("Routing GET request");
    //   GetStockHandler.handle(request);
    // }
    return new Response();
  }
}