package nl.geostandaarden.imx.fieldlab.woz.source.rest;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import nl.geostandaarden.imx.orchestrate.engine.exchange.CollectionRequest;
import nl.geostandaarden.imx.orchestrate.engine.exchange.ObjectRequest;
import nl.geostandaarden.imx.orchestrate.engine.source.DataRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@RequiredArgsConstructor
public class RestRepository implements DataRepository {

  private final HttpClient httpClient;

  @Override
  public Mono<Map<String, Object>> findOne(ObjectRequest request) {
    return Mono.empty();
  }

  @Override
  public Flux<Map<String, Object>> find(CollectionRequest request) {
    return Flux.empty();
  }
}
