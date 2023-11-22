package nl.geostandaarden.imx.fieldlab.woz.source.rest;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.geostandaarden.imx.orchestrate.engine.source.DataRepository;
import nl.geostandaarden.imx.orchestrate.engine.source.Source;
import reactor.netty.http.client.HttpClient;

public class RestSource implements Source {

  private final RestRepository repository;

  public RestSource(Options options) {
    var client = HttpClient.create()
        .baseUrl(options.getBaseUrl())
        .headers(builder -> options.getHeaders()
            .entrySet()
            .forEach(entry -> builder.add(entry.getKey(), entry.getValue())));

    repository = new RestRepository(client, options.getPaths());
  }

  @Override
  public DataRepository getDataRepository() {
    return repository;
  }

  @Getter
  @RequiredArgsConstructor
  public static class Options {

    private final String baseUrl;

    private final Map<String, String> headers;

    private final Map<String, String> paths;
  }
}
