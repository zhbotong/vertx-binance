package icu.zhangbotong.trade;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;

public class WebSocketStreamClient {

  public static WebsocketAPI create(Vertx vertx){
    return create(vertx,new HttpClientOptions());
  }

  public static WebsocketAPI create(Vertx vertx,HttpClientOptions httpClientOptions){
    HttpClient httpClient = vertx.createHttpClient(httpClientOptions);
    return new WebsocketAPI(httpClient);
  }
}
