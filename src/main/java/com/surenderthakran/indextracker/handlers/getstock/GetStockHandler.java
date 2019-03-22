package com.surenderthakran.indextracker.handlers.getstock;

import com.google.common.flogger.FluentLogger;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;
import com.surenderthakran.indextracker.net.Handler;
import com.surenderthakran.indextracker.net.Request;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class GetStockHandler implements Handler {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @Override
  public JsonElement handle(HttpExchange exchange, Request req) throws IOException {
    GetStockRequest request = (GetStockRequest) req;
    logger.atInfo().log("Handling GetStock request");

    System.out.println(exchange.getRequestURI());
    System.out.println(exchange.getLocalAddress());
    System.out.println(exchange.getRequestMethod());

    InputStream inputStream = exchange.getRequestBody();
    String body = CharStreams.toString(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    System.out.println(body);

    Gson gson = new Gson();

    GetStockResponse getStockResponse = new GetStockResponse();
    getStockResponse.id = request.id;
    getStockResponse.open = 12;
    getStockResponse.high = 15;
    getStockResponse.low = 10;
    getStockResponse.close = 13;

    return gson.toJsonTree(getStockResponse);
  }
}
