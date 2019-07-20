package by.vorobyov.transfer.utils;

import static org.junit.Assert.*;

import by.vorobyov.transfer.domain.TransferRequest;
import org.junit.Test;

import java.math.BigDecimal;

public class RequestValidatorTest {

  @Test
  public void validate_testSuccessValidation() {
    // Given
    TransferRequest transferRequest = new TransferRequest(1,111,222,new BigDecimal(123.32));

    // When
    boolean result = RequestValidator.isValid(transferRequest);

    // Then
    assertTrue(result);
  }

  @Test
  public void validate_testNegativeAmount() {
    // Given
    TransferRequest transferRequest = new TransferRequest(1,111,222,new BigDecimal(-123.32));

    // When
    boolean result = RequestValidator.isValid(transferRequest);

    // Then
    assertFalse(result);
  }

  @Test
  public void validate_testNegativeAccountOrigin() {
    // Given
    TransferRequest transferRequest = new TransferRequest(1,111,-222,new BigDecimal(123.32));

    // When
    boolean result = RequestValidator.isValid(transferRequest);

    // Then
    assertFalse(result);
  }

  @Test
  public void validate_testNegativeAccountDestination() {
    // Given
    TransferRequest transferRequest = new TransferRequest(1,-111,222,new BigDecimal(123.32));

    // When
    boolean result = RequestValidator.isValid(transferRequest);

    // Then
    assertFalse(result);
  }

  @Test
  public void validate_testNegativeUserId() {
    // Given
    TransferRequest transferRequest = new TransferRequest(-1,111,222,new BigDecimal(123.32));

    // When
    boolean result = RequestValidator.isValid(transferRequest);

    // Then
    assertFalse(result);
  }


}