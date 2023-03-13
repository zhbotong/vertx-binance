package icu.zhangbotong.trade;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.ProxyOptions;
import io.vertx.core.net.ProxyType;


public class Demo {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    HttpClientOptions httpClientOptions = new HttpClientOptions();
//    ProxyOptions proxyOptions = new ProxyOptions();
//    proxyOptions.setHost("127.0.0.1");
//    proxyOptions.setPort(8889);
//    proxyOptions.setType(ProxyType.HTTP);
//    httpClientOptions.setProxyOptions(proxyOptions);
//    WebsocketAPI websocketAPI = WebSocketStreamClient.create(vertx, httpClientOptions);
//    websocketAPI.singeTradeStream("vgxusdt", System.out::println);

    TradleAPI tradleAPI = TradeClient.create(vertx, httpClientOptions);

    JsonObject param = new JsonObject();
    param.put("symbol","LTCBTC");
    tradleAPI.connect("/api/v3/order",param)
      .onSuccess(event -> System.out.println(event.encodePrettily()))
      .onFailure(Throwable::printStackTrace);
  }
}
