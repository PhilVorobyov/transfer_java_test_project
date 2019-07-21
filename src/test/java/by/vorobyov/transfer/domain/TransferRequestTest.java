package by.vorobyov.transfer.domain;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import java.math.BigDecimal;

public class TransferRequestTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Test
  public void transferRequest_serializeToJSON() throws Exception {
    final TransferRequest person = new TransferRequest(2,111, 222, new BigDecimal(1234.23432));

    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("transfer_request.json"), TransferRequest.class));
    (MAPPER.writeValueAsString(person)).equals(expected);
  }
}