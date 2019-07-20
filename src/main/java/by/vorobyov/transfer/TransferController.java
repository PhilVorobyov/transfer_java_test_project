package by.vorobyov.transfer;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import by.vorobyov.transfer.service.TransferService;
import by.vorobyov.transfer.utils.RequestValidator;
import by.vorobyov.transfer.domain.TransferRequest;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class TransferController {

  private TransferService transferService;

  public TransferController(final TransferService transferService) {
    this.transferService = transferService;
  }

  @PUT
  @Path("/transfer")
  public Response transfer(@Valid TransferRequest transferRequest) {
    log.info("transfer request: " + transferRequest);
    if (RequestValidator.isValid(transferRequest)) {
      return transferService.transfer(transferRequest);
    }
    else {
      log.warn("transfer request is not valid: " + transferRequest);
      return Response.status(BAD_REQUEST)
          .type(MediaType.APPLICATION_JSON)
          .entity("Can't transfer required amount due to bad request")
          .build();
    }

  }
}