package nl.geostandaarden.imx.fieldlab.woz.source.rest;

import nl.geostandaarden.imx.orchestrate.engine.source.DataRepository;
import nl.geostandaarden.imx.orchestrate.engine.source.Source;

public class RestSource implements Source {

  private final RestRepository repository = new RestRepository();

  @Override
  public DataRepository getDataRepository() {
    return repository;
  }
}
