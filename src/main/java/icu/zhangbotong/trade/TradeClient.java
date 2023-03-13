package icu.zhangbotong.trade;


import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;

public class TradeClient {
  public static TradleAPI create(Vertx vertx, HttpClientOptions clientOptions){
    HttpClient httpClient = vertx.createHttpClient(clientOptions);
    return new TradleAPI(httpClient);
  }
}
