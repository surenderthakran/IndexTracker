package com.surenderthakran.indextracker.net;

import com.sun.net.httpserver.HttpHandler;

public class Route {
  public String url;
  public Request request;
  public Class responseClass;
  public HttpHandler handler;

  public Route(String url, Request request, Class responseClass, HttpHandler handler) {
    this.url = url;
    this.request = request;
    this.responseClass = responseClass;
    this.handler = handler;
  }
}
