package com.surenderthakran.indextracker;

import com.google.common.collect.ImmutableList;
import com.google.common.flogger.FluentLogger;
import com.google.gson.reflect.TypeToken;
import com.surenderthakran.indextracker.handlers.getstock.GetStockHandler;
import com.surenderthakran.indextracker.handlers.getstock.GetStockRequest;
import com.surenderthakran.indextracker.handlers.getstock.GetStockResponse;
import com.surenderthakran.indextracker.net.RequestMethod;
import com.surenderthakran.indextracker.net.Route;
import com.surenderthakran.indextracker.net.Router;
import com.surenderthakran.indextracker.net.Server;
import java.io.IOException;

class App {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public static void main(String[] args) throws IOException {
    ImmutableList<Route> routes =
        ImmutableList.<Route>builder()
            .add(
                new Route(
                    "/getstock",
                    RequestMethod.POST,
                    new TypeToken<GetStockRequest>() {},
                    GetStockResponse.class,
                    new GetStockHandler()))
            .build();

    Server server =
        Server.builder()
            .setPort(18990)
            .setRouter(Router.builder().setRoutes(routes).build())
            .build();

    server.start();
  }
}
