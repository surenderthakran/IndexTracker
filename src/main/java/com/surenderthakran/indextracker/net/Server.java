package com.surenderthakran.indextracker.net;

import com.google.common.io.CharStreams;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class Server {
  private HttpServer httpServer;

  private Server(Builder builder) throws IOException {
    this.httpServer = HttpServer.create(new InetSocketAddress(builder.port), builder.backlog);

    // server.createContext("/", App::handleGetRequest);
    this.httpServer.createContext("/", builder.router);

    // Runs incoming requests in separate threads. It creates a new thread for each incoming request
    // and can technically result in unlimited threads being created. Alternatively, we can use
    // Executors.newFixedThreadPool().
    this.httpServer.setExecutor(Executors.newCachedThreadPool());
  }

  public void start() {
    this.httpServer.start();
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

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private int port;
    private int backlog = 0;
    private Router router;

    public Builder setPort(int port) {
      this.port = port;
      return this;
    }

    public Builder setBacklog(int backlog) {
      this.backlog = backlog;
      return this;
    }

    public Builder setRouter(Router router) {
      this.router = router;
      return this;
    }

    public Server build() throws IOException {
      return new Server(this);
    }
  }
}
