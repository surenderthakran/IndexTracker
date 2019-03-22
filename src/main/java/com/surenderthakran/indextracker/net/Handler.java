package com.surenderthakran.indextracker.net;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public interface Handler {
  public JsonElement handle(HttpExchange exchange, Request request) throws IOException;
}
