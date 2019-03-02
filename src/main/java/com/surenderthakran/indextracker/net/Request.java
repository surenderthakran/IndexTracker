package com.surenderthakran.indextracker.net;

import com.surenderthakran.indextracker.net.exceptions.InvalidRequestException;

public class Request {
  HTTPMethod method;

  enum HTTPMethod {
    GET,
    POST
  }

  String uri;
  String queryString;

  boolean reqLineRead = false;

  HTTPMethod method() {
    return this.method;
  }

  Request setMethod(HTTPMethod method) {
    this.method = method;
    return this;
  }

  String uri() {
    return this.uri;
  }

  Request setUri(String uri) {
    this.uri = uri;
    return this;
  }

  @Override
  public String toString() {
    return String.format("Request: %s %s", this.method(), this.uri());
  }

  boolean isRequestLine(String line) {
    line = line.toLowerCase().trim().replaceAll(" +", " ");
    String[] reqLineComponents = line.split("\\s+");
    if (reqLineComponents[0].equals("get") || reqLineComponents[0].equals("post")) {
      return true;
    }
    return false;
  }

  void readRequestLine(String line) throws InvalidRequestException {
    // If Request Line is already read once.
    if (reqLineRead) {
      throw new InvalidRequestException("Multiple request lines found.");
    }

    line = line.toLowerCase().trim().replaceAll(" +", " ");
    String[] reqLineComponents = line.split("\\s+");
    if (reqLineComponents[0].equals("get")) {
      this.setMethod(HTTPMethod.GET);
    } else if (reqLineComponents[0].equals("post")) {
      this.setMethod(HTTPMethod.POST);
    }

    if (reqLineComponents.length >= 2) {
      this.setUri(reqLineComponents[1]);
      if (this.method() == HTTPMethod.GET) {
        this.readQueryString();
      }
    }
    this.reqLineRead = true;
  }

  void readQueryString() {
    String uri = this.uri();
    String queryString = "";
    int queryStartIndex = uri.indexOf('?');
    if (queryStartIndex > -1) {
      int queryEndIndex = uri.indexOf('#', queryStartIndex);
      if (queryEndIndex == -1) {
        queryEndIndex = uri.length() - 1;
      }
      queryString = uri.substring(queryStartIndex, queryEndIndex + 1);
    }
    this.queryString = queryString;
  }
}
