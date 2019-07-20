package by.vorobyov.transfer.service;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import by.vorobyov.transfer.domain.Representation;
import by.vorobyov.transfer.dao.TransferDao;
import by.vorobyov.transfer.domain.Account;
import by.vorobyov.transfer.domain.TransferRequest;
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
        int accountOrigin = transferRequest.getAccountOrigin();
        int accountDestination = transferRequest.getAccountDestination();
        BigDecimal amount = transferRequest.getAmount();

        Account origin = transferDao.getAccountByNumber(accountOrigin);
        if (origin == null) {
          log.warn("No such origin account: " + accountOrigin);
          throw new Exception("No such account: " + accountOrigin);
        }
        Account destination = transferDao.getAccountByNumber(accountDestination);
        if (destination == null) {
          log.warn("No such destination account: " + accountDestination);
          throw new Exception("No such destination account: " + accountDestination);
        }

        if (origin.getAmount().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
          log.warn("not enough amount on current balance: " + origin.getAmount());
          throw new Exception("not enough on your balance");
        }

        transferDao.updateAccount(accountOrigin, origin.getAmount().subtract(amount));
        transferDao.updateAccount(accountDestination, destination.getAmount().add(amount));

        log.info("amount was successfully transfered");

        return Response.status(Status.OK)
            .type(MediaType.APPLICATION_JSON_TYPE)
            .entity(new Representation("Amount:" + amount + " was successfully transfered from account:" + origin.getAccount_number()
                + " to account: " + destination.getAccount_number()))
            .build();

      } catch (Exception e) {
        log.error("can't transfer amount due to unexpected exception: " + e);
        return Response.status(BAD_REQUEST)
            .type(MediaType.APPLICATION_JSON_TYPE)
            .entity(new Representation("Can't transfer required amount:" + e.getMessage()))
            .build();
      }
    });
  }
}
