package nl.geostandaarden.imx.fieldlab.woz.source.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nl.geostandaarden.imx.orchestrate.engine.exchange.BatchRequest;
import nl.geostandaarden.imx.orchestrate.engine.exchange.CollectionRequest;
import nl.geostandaarden.imx.orchestrate.engine.exchange.DataRequest;
import nl.geostandaarden.imx.orchestrate.engine.exchange.ObjectRequest;
import nl.geostandaarden.imx.orchestrate.engine.source.DataRepository;
import nl.geostandaarden.imx.orchestrate.model.ObjectType;
import org.springframework.vault.support.JsonMapFlattener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@RequiredArgsConstructor
public class RestRepository implements DataRepository {

  private final ObjectMapper jsonMapper = new JsonMapper()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  private final HttpClient httpClient;

  private final Map<String, String> paths;

  @Override
  public Mono<Map<String, Object>> findOne(ObjectRequest request) {
    return httpClient.get()
        .uri(getObjectURI(request))
        .responseSingle((response, content) -> content.asInputStream())
        .map(this::parseObject)
        .map(JsonMapFlattener::flatten)
        .onErrorComplete();
  }

  @Override
  public Flux<Map<String, Object>> find(CollectionRequest request) {
    return httpClient.get()
        .uri(getCollectionURI(request))
        .responseSingle((response, content) -> content.asInputStream())
        .map(this::parseCollection)
        .flatMapMany(resource -> Flux.fromIterable(resource.getData()))
        .map(JsonMapFlattener::flatten)
        .onErrorComplete();
  }

  @Override
  public Flux<Map<String, Object>> findBatch(BatchRequest request) {
    return httpClient.get()
        .uri(getBatchURI(request))
        .responseSingle((response, content) -> content.asInputStream())
        .map(this::parseBatch)
        .flatMapMany(Flux::fromIterable)
        .map(JsonMapFlattener::flatten)
        .onErrorComplete();
  }

  @Override
  public boolean supportsBatchLoading(ObjectType objectType) {
    return true;
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

  private String getCollectionURI(DataRequest request) {
    var typeName = request.getObjectType()
        .getName();

    var path = Optional.ofNullable(paths.get(typeName))
        .orElse(typeName);

    return "/" + path;
  }

  private String getBatchURI(BatchRequest request) {
    return getCollectionURI(request)
        .concat("-many");
  }

  private ObjectResource parseObject(InputStream content) {
    try {
      return jsonMapper.readValue(content, ObjectResource.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private CollectionResource parseCollection(InputStream content) {
    try {
      return jsonMapper.readValue(content, CollectionResource.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private BatchResource parseBatch(InputStream content) {
    try {
      return jsonMapper.readValue(content, BatchResource.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
