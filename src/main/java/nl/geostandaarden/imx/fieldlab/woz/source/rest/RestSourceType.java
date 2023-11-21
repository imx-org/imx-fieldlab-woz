package nl.geostandaarden.imx.fieldlab.woz.source.rest;

import com.google.auto.service.AutoService;
import java.util.Map;
import java.util.Optional;
import nl.geostandaarden.imx.orchestrate.engine.source.Source;
import nl.geostandaarden.imx.orchestrate.engine.source.SourceType;
import nl.geostandaarden.imx.orchestrate.model.Model;

@AutoService(SourceType.class)
public class RestSourceType implements SourceType {

  private static final String SOURCE_TYPE = "rest";

  @Override
  public String getName() {
    return SOURCE_TYPE;
  }

  @Override
  public Source create(Model model, Map<String, Object> options) {
    var baseUrl = Optional.ofNullable(options.get("baseUrl"))
        .map(String.class::cast)
        .orElseThrow(() -> new RuntimeException(
            String.format("Source '%s' requires a 'baseUrl' option.", model.getAlias())));

    return new RestSource(new RestSource.Options(baseUrl, Map.of()));
  }
}
