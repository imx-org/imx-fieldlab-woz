package nl.geostandaarden.imx.fieldlab.woz.source.rest;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import java.util.Map;
import javax.net.ssl.SSLException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.geostandaarden.imx.orchestrate.engine.source.DataRepository;
import nl.geostandaarden.imx.orchestrate.engine.source.Source;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.SslProvider;

public class RestSource implements Source {

  private final RestRepository repository;

  public RestSource(Options options) {
    var client = HttpClient.create()
        .baseUrl(options.getBaseUrl())
        .headers(builder -> options.getHeaders()
            .entrySet()
            .forEach(entry -> builder.add(entry.getKey(), entry.getValue())))
        .secure(this::configureSslContext);

    repository = new RestRepository(client, options.getPaths());
  }

  private void configureSslContext(SslProvider.SslContextSpec contextSpec) {
    try {
      contextSpec.sslContext(SslContextBuilder.forClient()
          .trustManager(InsecureTrustManagerFactory.INSTANCE)
          .build());
    } catch (SSLException e) {
      throw new RuntimeException(e);
    }
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
