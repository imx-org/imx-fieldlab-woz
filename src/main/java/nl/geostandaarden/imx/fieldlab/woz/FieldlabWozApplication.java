package nl.geostandaarden.imx.fieldlab.woz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("nl.geostandaarden.imx")
public class FieldlabWozApplication {

  public static void main(String[] args) {
    SpringApplication.run(FieldlabWozApplication.class, args);
  }
}
