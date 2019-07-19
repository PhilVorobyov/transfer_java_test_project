package by.vorobyov;

import static by.vorobyov.utils.RequestValidator.validate;

import by.vorobyov.domain.TransferRequest;
import by.vorobyov.service.TransferService;
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
    if (validate(transferRequest)) {
      transferService.transfer(transferRequest);
      return Response.ok().build();
    }
    else {
      log.error("transfer request is not valid: " + transferRequest);
      return Response.serverError().build();
    }

  }
}