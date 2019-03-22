package com.surenderthakran.indextracker.net;

import com.google.common.collect.ImmutableList;
import com.google.common.flogger.FluentLogger;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.surenderthakran.indextracker.handlers.getstock.GetStockHandler;
import com.surenderthakran.indextracker.handlers.getstock.GetStockRequest;
import com.surenderthakran.indextracker.handlers.getstock.GetStockResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Router implements HttpHandler {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private static ImmutableList<Route> routes =
      ImmutableList.<Route>builder()
          .add(
              new Route(
                  "/getstock",
                  new TypeToken<GetStockRequest>() {},
                  GetStockResponse.class,
                  new GetStockHandler()))
          .build();

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    logger.atInfo().log("Routing request");

    System.out.println(exchange.getRequestURI());
    System.out.println("===================================================");
    System.out.println(exchange.getRequestURI().getScheme());
    System.out.println(exchange.getRequestURI().getAuthority());
    System.out.println(exchange.getRequestURI().getHost());
    System.out.println(exchange.getRequestURI().getPath());
    System.out.println(exchange.getRequestURI().getPort());
    System.out.println(exchange.getRequestURI().getQuery());
    System.out.println(exchange.getRequestURI().getFragment());
    System.out.println("===================================================");
    System.out.println(exchange.getLocalAddress());
    System.out.println(exchange.getRequestMethod());

    InputStream inputStream = exchange.getRequestBody();
    String body = CharStreams.toString(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    System.out.println(body);

    Gson gson = new Gson();

    for (Route route : routes) {
      if (route.url.equals(exchange.getRequestURI().getPath())) {
        System.out.println("URI match found");

        // Type requestType = new TypeToken<route.request>() {}.getType();
        // GetStockRequest getStockRequest = gson.fromJson(body, route.requestTypeToken.getType());

        // GetStockRequest getStockRequest = gson.fromJson(body, route.request.getClass());

        // GetStockRequest getStockRequest = gson.fromJson(body, GetStockRequest.class);

        // System.out.println(getStockRequest.id);

        route.handler.handle(exchange, gson.fromJson(body, route.requestTypeToken.getType()));

        // GetStockResponse getStockResponse = new GetStockResponse();
        // getStockResponse.id = getStockRequest.id;
        // getStockResponse.id = "reliance";
        // getStockResponse.open = 12;
        // getStockResponse.high = 15;
        // getStockResponse.low = 10;
        // getStockResponse.close = 13;

        // String response = gson.toJson(getStockResponse);
        // String response =
        // "{\"id\":\"reliance\",\"open\":12,\"high\":15,\"low\":10,\"close\":13}";
        // System.out.println(response);

        // exchange.sendResponseHeaders(200, response.getBytes().length); // response code and
        // length
        // OutputStream os = exchange.getResponseBody();
        // os.write(response.getBytes());
        // os.close();
      }
    }
  }
}