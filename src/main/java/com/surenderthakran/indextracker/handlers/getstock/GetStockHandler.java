package com.surenderthakran.indextracker.handlers.getstock;

import com.google.common.flogger.FluentLogger;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class GetStockHandler implements HttpHandler {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    logger.atInfo().log("Handling GetStock request");

    System.out.println(exchange.getRequestURI());
    System.out.println(exchange.getLocalAddress());
    System.out.println(exchange.getRequestMethod());

    InputStream inputStream = exchange.getRequestBody();
    String body = CharStreams.toString(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    System.out.println(body);

    Gson gson = new Gson();

    // GetStockRequest getStockRequest =
    //     gson.fromJson(
    //         body,
    //         Class.forName("com.surenderthakran.indextracker.handlers.getstock.getStockRequest"));
    // System.out.println(getStockRequest.id);
    //
    GetStockResponse getStockResponse = new GetStockResponse();
    // getStockResponse.id = getStockRequest.id;
    getStockResponse.id = "reliance";
    getStockResponse.open = 12;
    getStockResponse.high = 15;
    getStockResponse.low = 10;
    getStockResponse.close = 13;

    String response = gson.toJson(getStockResponse);
    System.out.println(response);
    // String response = "{\"id\":\"reliance\",\"open\":12,\"high\":15,\"low\":10,\"close\":13}";
    exchange.sendResponseHeaders(200, response.getBytes().length); // response code and length
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }
}
