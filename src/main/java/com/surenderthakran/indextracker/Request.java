package com.surenderthakran.indextracker;

public class Request {
  String method;

  String method() {
    return this.method;
  }

  Request setMethod(String method) {
    this.method = method;
    return this;
  }

  @Override
  public String toString() {
    return "Request: " + method;
  }
}
