package com.surenderthakran.indextracker.handlers.getstock;

import com.google.common.flogger.FluentLogger;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;
import com.surenderthakran.indextracker.net.Handler;
import com.surenderthakran.indextracker.net.Request;

public class GetStockHandler implements Handler {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @Override
  public JsonElement handle(HttpExchange exchange, Request req) {
    GetStockRequest request = (GetStockRequest) req;
    logger.atInfo().log("Handling GetStock request");

    Gson gson = new Gson();

    GetStockResponse getStockResponse = new GetStockResponse();
    getStockResponse.setId(request.getId());
    getStockResponse.setOpen(12);
    getStockResponse.setHigh(15);
    getStockResponse.setLow(10);
    getStockResponse.setClose(13);

    return gson.toJsonTree(getStockResponse);
  }
}
