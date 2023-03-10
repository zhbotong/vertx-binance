package icu.zhangbotong.trade;

import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.WebsocketVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class WebsocketAPI {
  private final HttpClient httpClient;

  private static final Logger log = LoggerFactory.getLogger(WebsocketAPI.class);


  public WebsocketAPI(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  /**
   * 逐笔交易
   * @param symbol
   * @param handler
   */
  public void singeTradeStream(String symbol,Handler<String> handler) {
    connect(symbol+"@trade",handler);
  }

  private void connect(String symbol, Handler<String> handler) {
    httpClient
      .webSocketAbs(String.join("",BinanceApi.WSS, symbol), MultiMap.caseInsensitiveMultiMap(), WebsocketVersion.V13, List.of())
      .onFailure(Throwable::printStackTrace)
      .onSuccess(ws ->{
        ws.handler(event -> handler.handle(event.toString()));
        ws.frameHandler(frame -> {
          if (frame.isPing()){
            log.debug("Received ping message from server!");
            ws.writePong(frame.binaryData());
          }
        });
        ws.closeHandler(event -> reconnect(symbol,handler));
      });
  }

  private void reconnect(String symbol,Handler<String> handler) {
    // 重连延迟（毫秒）
    long RECONNECT_DELAY = 1000L;
    log.debug("Attempting to reconnect in {} ms...", RECONNECT_DELAY);
    Vertx.vertx().setTimer(RECONNECT_DELAY,event -> connect(symbol,handler));
  }


}
