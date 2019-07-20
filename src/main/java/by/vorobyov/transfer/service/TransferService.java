package by.vorobyov.transfer.service;

import by.vorobyov.transfer.dao.TransferDao;
import by.vorobyov.transfer.domain.Account;
import by.vorobyov.transfer.domain.TransferRequest;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class TransferService {

  TransferDao transferDao;

  public TransferService(TransferDao transferDao) {
    this.transferDao = transferDao;
  }

  public void transfer(final TransferRequest transferRequest) {
    int userId = transferRequest.getUserId();
    int accountOrigin = transferRequest.getAccountOrigin();
    int accountDestination = transferRequest.getAccountDestination();
    BigDecimal amount = transferRequest.getAmount();

    Account origin = transferDao.getAccountByNumber(accountOrigin);
    Account destination = transferDao.getAccountByNumber(accountDestination);

    if (origin.getAmount().subtract(amount).compareTo(BigDecimal.ZERO) > 0) {
      log.warn("not enough amount on current balance: " + origin.getAmount());
      System.out.println("fail");
    }

    transferDao.updateAccount(accountOrigin, origin.getAmount().subtract(amount));
    transferDao.updateAccount(accountDestination, destination.getAmount().add(amount));

    Account origin1 = transferDao.getAccountByNumber(accountOrigin);
    Account destination1 = transferDao.getAccountByNumber(accountDestination);
  }
}
