package com.surenderthakran.indextracker;

import com.google.common.flogger.FluentLogger;
import com.google.common.io.CharStreams;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.surenderthakran.indextracker.net.Router;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

class App {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public static void main(String[] args) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(18990), 0);

    server.createContext("/", App::handleGetRequest);
    server.createContext("/getstock", new Router());

    // Runs incoming requests in separate threads. It creates a new thread for each incoming request
    // and can technically result in unlimited threads being created. Alternatively, we can use
    // Executors.newFixedThreadPool().
    server.setExecutor(Executors.newCachedThreadPool());
    server.start();
  }

  private static void handleGetRequest(HttpExchange exchange) throws IOException {
    System.out.println(exchange.getRequestURI());
    System.out.println(exchange.getLocalAddress());
    System.out.println(exchange.getRequestMethod());

    InputStream inputStream = exchange.getRequestBody();
    System.out.println(
        CharStreams.toString(new InputStreamReader(inputStream, StandardCharsets.UTF_8)));

    String response = "Hi get!";
    exchange.sendResponseHeaders(200, response.getBytes().length); // response code and length
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }
}
