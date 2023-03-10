package icu.zhangbotong.trade;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.net.ProxyOptions;
import io.vertx.core.net.ProxyType;


public class Demo {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    HttpClientOptions httpClientOptions = new HttpClientOptions();
    ProxyOptions proxyOptions = new ProxyOptions();
    proxyOptions.setHost("127.0.0.1");
    proxyOptions.setPort(7890);
    proxyOptions.setType(ProxyType.HTTP);
    httpClientOptions.setProxyOptions(proxyOptions);
    WebsocketAPI websocketAPI = WebSocketStreamClient.create(vertx, httpClientOptions);
    websocketAPI.singeTradeStream("vgxusdt", System.out::println);
  }
}
