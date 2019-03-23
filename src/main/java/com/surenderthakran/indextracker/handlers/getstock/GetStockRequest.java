package com.surenderthakran.indextracker.handlers.getstock;

import com.surenderthakran.indextracker.net.Request;

public class GetStockRequest implements Request {
  private String id;

  public String getId() {
    return this.id;
  }
}
