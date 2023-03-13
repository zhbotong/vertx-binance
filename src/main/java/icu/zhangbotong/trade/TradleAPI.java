package icu.zhangbotong.trade;

import io.vertx.core.Future;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.stream.Collectors;

public class TradleAPI {
  private final HttpClient httpClient;

  private static final Logger log = LoggerFactory.getLogger(WebsocketAPI.class);


  public TradleAPI(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  public Future<JsonObject> connect(String url, JsonObject param){
    WebClient webClient = WebClient.wrap(httpClient);
    param.put("timestamp",System.currentTimeMillis());
    String sign = sign(param);
    param.put("signature",sign);
    return webClient.postAbs(BinanceApi.TRADLE+url)
      .putHeader("X-MBX-APIKEY",BinanceAPIKey.API_KEY)
      .sendJsonObject(param)
      .map(HttpResponse::bodyAsJsonObject);
  }

  public String sign(JsonObject param){
    String signParam = param.stream()
      .map(entry -> entry.getKey() + "=" + entry.getValue())
      .collect(Collectors.joining("&"));
    try {
      Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
      SecretKeySpec secret_key = new SecretKeySpec(BinanceAPIKey.SECRET_KEY.getBytes(), "HmacSHA256");
      sha256_HMAC.init(secret_key);
      byte[] hash = sha256_HMAC.doFinal(signParam.getBytes());
      return Base64.getEncoder().encodeToString(hash);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
