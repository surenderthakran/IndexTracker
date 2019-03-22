package com.surenderthakran.indextracker.net;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public interface Handler {
  public void handle(HttpExchange exchange, Request request) throws IOException;
}
