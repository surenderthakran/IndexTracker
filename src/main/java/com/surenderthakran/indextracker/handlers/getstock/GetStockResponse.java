package com.surenderthakran.indextracker.handlers.getstock;

public class GetStockResponse {
  private String id;
  private int open;
  private int high;
  private int low;
  private int close;

  public void setId(String id) {
    this.id = id;
  }

  public void setOpen(int open) {
    this.open = open;
  }

  public void setHigh(int high) {
    this.high = high;
  }

  public void setLow(int low) {
    this.low = low;
  }

  public void setClose(int close) {
    this.close = close;
  }
}
