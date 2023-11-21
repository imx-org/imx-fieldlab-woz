package nl.geostandaarden.imx.fieldlab.woz.source.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nl.geostandaarden.imx.orchestrate.engine.exchange.CollectionRequest;
import nl.geostandaarden.imx.orchestrate.engine.exchange.DataRequest;
import nl.geostandaarden.imx.orchestrate.engine.exchange.ObjectRequest;
import nl.geostandaarden.imx.orchestrate.engine.source.DataRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@RequiredArgsConstructor
public class RestRepository implements DataRepository {

  private final JsonMapper jsonMapper = new JsonMapper();

  private final HttpClient httpClient;

  private final Map<String, String> paths;

  @Override
  public Mono<Map<String, Object>> findOne(ObjectRequest request) {
    return httpClient.get()
        .uri(getObjectURI(request))
        .responseSingle((response, content) -> content.asInputStream())
        .map(this::parseContent);
  }

  @Override
  public Flux<Map<String, Object>> find(CollectionRequest request) {
    return Flux.empty();
  }

  private String getCollectionURI(DataRequest request) {
    var typeName = request.getObjectType()
        .getName();

    var path = Optional.ofNullable(paths.get(typeName))
        .orElse(typeName);

    return "/" + path;
  }

  private String getObjectURI(ObjectRequest request) {
    var objectKey = request.getObjectKey()
        .values()
        .iterator()
        .next()
        .toString();

    return getCollectionURI(request)
        .concat("/" + objectKey);
  }

  private Map<String, Object> parseContent(InputStream content) {
    var typeRef = new TypeReference<Map<String, Object>>() {};

    try {
      return jsonMapper.readValue(content, typeRef);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
