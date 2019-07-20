package by.vorobyov.transfer.utils;

import by.vorobyov.transfer.domain.TransferRequest;

import java.math.BigDecimal;

public class RequestValidator {
  public static boolean isValid(final TransferRequest transferRequest) {
    return validateAmount(transferRequest)
        && validateOriginAccount(transferRequest)
        && validatedestinationAccount(transferRequest)
        && validateUserId(transferRequest);
  }

  private static boolean validateAmount(final TransferRequest transferRequest) {
    return transferRequest.getAmount().compareTo(BigDecimal.ZERO) > 0;
  }

  private static boolean validateOriginAccount(final TransferRequest transferRequest) {
    return transferRequest.getAccountOrigin() > 0;
  }

  private static boolean validatedestinationAccount(final TransferRequest transferRequest) {
    return transferRequest.getAccountDestination() > 0;
  }

  private static boolean validateUserId(final TransferRequest transferRequest) {
    return transferRequest.getUserId() > 0;
  }
}
