package by.vorobyov.transfer.service;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import by.vorobyov.transfer.dao.TransferDao;
import by.vorobyov.transfer.domain.Account;
import by.vorobyov.transfer.domain.TransferRequest;
import io.dropwizard.jersey.errors.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.skife.jdbi.v2.DBI;

import java.math.BigDecimal;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Slf4j
public class TransferService {

  private DBI dbi;

  public TransferService(DBI dbi) {
    this.dbi = dbi;
  }

  public Response transfer(final TransferRequest transferRequest) {
    return dbi.inTransaction((conn, status) -> {
      try {
        TransferDao transferDao = conn.attach(TransferDao.class);
        int userId = transferRequest.getUserId();
        int accountOrigin = transferRequest.getAccountOrigin();
        int accountDestination = transferRequest.getAccountDestination();
        BigDecimal amount = transferRequest.getAmount();

        Account origin = transferDao.getAccountByNumber(accountOrigin);
        Account destination = transferDao.getAccountByNumber(accountDestination);

        if (origin.getAmount().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
          log.warn("not enough amount on current balance: " + origin.getAmount());
          System.out.println("fail");
        }

        transferDao.updateAccount(accountOrigin, origin.getAmount().subtract(amount));
        Account origin1 = transferDao.getAccountByNumber(accountOrigin);
        doMagic();
        transferDao.updateAccount(accountDestination, destination.getAmount().add(amount));
        Account destination1 = transferDao.getAccountByNumber(accountDestination);

        return Response.status(Status.OK)
            .type(MediaType.APPLICATION_JSON_TYPE)
            .entity("Amount was successfully transfered")
            .build();

      } catch (Exception e) {
        return Response.status(BAD_REQUEST)
            .type(MediaType.APPLICATION_JSON_TYPE)
            .entity(new ErrorMessage(BAD_REQUEST.getStatusCode(),
                "Can't transfer required amount"))
            .build();
      }
    });
  }

  private void doMagic() {
    throw new RuntimeException();
  }
}
