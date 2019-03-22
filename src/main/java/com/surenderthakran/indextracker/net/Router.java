package com.surenderthakran.indextracker.net;

import com.google.common.collect.ImmutableList;
import com.google.common.flogger.FluentLogger;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.surenderthakran.indextracker.handlers.getstock.GetStockHandler;
import com.surenderthakran.indextracker.handlers.getstock.GetStockRequest;
import com.surenderthakran.indextracker.handlers.getstock.GetStockResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Router implements HttpHandler {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private static ImmutableList<Route> routes =
      ImmutableList.<Route>builder()
          .add(
              new Route(
                  "/getstock",
                  RequestMethod.POST,
                  new TypeToken<GetStockRequest>() {},
                  GetStockResponse.class,
                  new GetStockHandler()))
          .build();

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    logger.atInfo().log("Routing request: %s", exchange.getRequestURI());

    InputStream inputStream = exchange.getRequestBody();
    String body = CharStreams.toString(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    System.out.println(body);

    Gson gson = new Gson();

    for (Route route : routes) {
      if (exchange.getRequestURI().getPath().equals(route.url)
          && exchange.getRequestMethod().equals(route.method.name())) {
        System.out.println("URI match found");

        JsonElement jsonEle =
            route.handler.handle(exchange, gson.fromJson(body, route.requestTypeToken.getType()));

        String response = gson.toJson(jsonEle);
        System.out.println(response);

        exchange.sendResponseHeaders(200, response.getBytes().length); // response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
      }
    }
  }
}
