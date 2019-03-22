package com.surenderthakran.indextracker.net;

import com.google.gson.reflect.TypeToken;

public class Route {
  public String url;
  public RequestMethod method;
  public TypeToken<? extends Request> requestTypeToken;
  public Class responseClass;
  public Handler handler;

  public Route(
      String url,
      RequestMethod method,
      TypeToken<? extends Request> requestTypeToken,
      Class responseClass,
      Handler handler) {
    this.url = url;
    this.method = method;
    this.requestTypeToken = requestTypeToken;
    this.responseClass = responseClass;
    this.handler = handler;
  }
}
