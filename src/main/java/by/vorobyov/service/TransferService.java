package by.vorobyov.service;

import by.vorobyov.dao.TransferDao;
import by.vorobyov.domain.TransferRequest;

public class TransferService {

  TransferDao transferDao;

  public TransferService(TransferDao transferDao) {
    this.transferDao = transferDao;
  }

  public void transfer(final TransferRequest transferRequest) {



  }

}
