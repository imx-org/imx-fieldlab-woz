package nl.geostandaarden.imx.fieldlab.woz.source.rest;

import nl.geostandaarden.imx.orchestrate.engine.source.DataRepository;
import nl.geostandaarden.imx.orchestrate.engine.source.Source;
import reactor.netty.http.client.HttpClient;

public class RestSource implements Source {

  private final RestRepository repository;

  public RestSource(String baseUrl) {
    var client = HttpClient.create()
        .baseUrl(baseUrl);

    repository = new RestRepository(client);
  }

  @Override
  public DataRepository getDataRepository() {
    return repository;
  }
}
