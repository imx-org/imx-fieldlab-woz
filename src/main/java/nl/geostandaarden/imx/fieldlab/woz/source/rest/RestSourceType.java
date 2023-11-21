package nl.geostandaarden.imx.fieldlab.woz.source.rest;

import com.google.auto.service.AutoService;
import java.util.Map;
import nl.geostandaarden.imx.orchestrate.engine.source.Source;
import nl.geostandaarden.imx.orchestrate.engine.source.SourceType;
import nl.geostandaarden.imx.orchestrate.model.Model;

@AutoService(RestSourceType.class)
public class RestSourceType implements SourceType {

  private static final String SOURCE_TYPE = "rest";

  @Override
  public String getName() {
    return SOURCE_TYPE;
  }

  @Override
  public Source create(Model model, Map<String, Object> map) {
    return new RestSource();
  }
}
