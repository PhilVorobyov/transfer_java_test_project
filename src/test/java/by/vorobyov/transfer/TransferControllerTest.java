package by.vorobyov.transfer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.dropwizard.testing.junit.DropwizardClientRule;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

public class TransferControllerTest {
  @Path("/ping")
  public static class PingResource {
    @GET
    public String ping() {
      return "pong";
    }
  }

  @ClassRule
  public static final DropwizardClientRule dropwizard = new DropwizardClientRule(new PingResource());

  @Test
  public void shouldPing() throws IOException {
    final URL url = new URL(dropwizard.baseUri() + "/ping");
    final String response = new BufferedReader(new InputStreamReader(url.openStream())).readLine();
    assertEquals("pong", response);
  }

  @ClassRule
  public static final DropwizardAppRule<DemoConfiguration> RULE =
      new DropwizardAppRule<DemoConfiguration>(DemoApplication.class, ResourceHelpers.resourceFilePath("my-app-config.yaml"));

  @Test
  public void loginHandlerRedirectsAfterPost() {
    Client client = new JerseyClientBuilder().build();

    Response response = client.target(
        String.format("http://localhost:%d/login", RULE.getLocalPort()))
        .request()
        .post(Entity.json(""));

    assertThat(response.getStatus()).isEqualTo(302);
  }

}