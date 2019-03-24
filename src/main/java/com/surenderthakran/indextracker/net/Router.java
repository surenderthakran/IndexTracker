package com.surenderthakran.indextracker.net;

import com.google.common.collect.ImmutableList;
import com.google.common.flogger.FluentLogger;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Router implements HttpHandler {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private ImmutableList<Route> routes;

  private Router(Builder builder) {
    this.routes = builder.routes;
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    logger.atInfo().log("Routing request: %s", exchange.getRequestURI());

    InputStream inputStream = exchange.getRequestBody();
    String body = CharStreams.toString(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    System.out.println(body);

    boolean matchFound = false;
    Gson gson = new Gson();

    for (Route route : routes) {
      if (exchange.getRequestURI().getPath().equals(route.getPath())
          && exchange.getRequestMethod().equals(route.getMethod().name())) {
        logger.atInfo().log("URI match found for: %s", exchange.getRequestURI());
        matchFound = true;

        JsonElement jsonEle =
            route
                .getHandler()
                .handle(exchange, gson.fromJson(body, route.getRequestTypeToken().getType()));

        String response = gson.toJson(jsonEle);
        System.out.println(response);

        exchange.sendResponseHeaders(200, response.getBytes().length); // response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
      }
    }

    if (!matchFound) {
      logger.atWarning().log("No URI match found for: %s", exchange.getRequestURI());

      String response = "Path not found";

      exchange.sendResponseHeaders(404, response.getBytes().length); // response code and length
      OutputStream os = exchange.getResponseBody();
      os.write(response.getBytes());
      os.close();
    }
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ImmutableList<Route> routes;

    public Builder setRoutes(ImmutableList<Route> routes) {
      this.routes = routes;
      return this;
    }

    public Router build() {
      return new Router(this);
    }
  }
}
