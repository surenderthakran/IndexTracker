package com.surenderthakran.indextracker.net;

import com.google.gson.reflect.TypeToken;

public class Route {
  private String path;
  private RequestMethod method;
  private TypeToken<? extends Request> requestTypeToken;
  private Class responseClass;
  private Handler handler;

  private Route(Builder builder) {
    this.path = builder.path;
    this.method = builder.method;
    this.requestTypeToken = builder.requestTypeToken;
    this.responseClass = builder.responseClass;
    this.handler = builder.handler;
  }

  public String getPath() {
    return this.path;
  }

  public RequestMethod getMethod() {
    return this.method;
  }

  public TypeToken<? extends Request> getRequestTypeToken() {
    return this.requestTypeToken;
  }

  public Handler getHandler() {
    return this.handler;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String path;
    private RequestMethod method;
    private TypeToken<? extends Request> requestTypeToken;
    private Class responseClass;
    private Handler handler;

    public Builder setPath(String path) {
      this.path = path;
      return this;
    }

    public Builder setMethod(RequestMethod method) {
      this.method = method;
      return this;
    }

    public Builder setRequestTypeToken(TypeToken<? extends Request> requestTypeToken) {
      this.requestTypeToken = requestTypeToken;
      return this;
    }

    public Builder setResponseClass(Class responseClass) {
      this.responseClass = responseClass;
      return this;
    }

    public Builder setHandler(Handler handler) {
      this.handler = handler;
      return this;
    }

    public Route build() {
      return new Route(this);
    }
  }
}
