package com.surenderthakran.indextracker.net;

import com.google.gson.reflect.TypeToken;

public class Route {
  public String url;
  public TypeToken<? extends Request> requestTypeToken;
  public Class responseClass;
  public Handler handler;

  public Route(
      String url,
      TypeToken<? extends Request> requestTypeToken,
      Class responseClass,
      Handler handler) {
    this.url = url;
    this.requestTypeToken = requestTypeToken;
    this.responseClass = responseClass;
    this.handler = handler;
  }
}
