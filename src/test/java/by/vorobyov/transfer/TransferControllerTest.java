package by.vorobyov.transfer;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.vorobyov.transfer.domain.TransferRequest;
import by.vorobyov.transfer.service.TransferService;
import org.junit.Test;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import javax.ws.rs.core.Response;

public class TransferControllerTest {


  @Test
  public void transfer_testSuccessfullTransfer() {
    // Given
    TransferService transferService = mock(TransferService.class);
    TransferRequest transferRequest = new TransferRequest(1,111,222,new BigDecimal(123.32));
    TransferController transferController = new TransferController(transferService);
    when(transferController.transfer(any())).then((Answer<?>) doNothing());

    // When
    Response response = transferController.transfer(transferRequest);

    // Then
    assertNotNull(response);
    verify(transferService, times(1)).transfer(any());
  }

  @Test
  public void transfer_testNegativeAccount() {
    // Given
    TransferService transferService = mock(TransferService.class);
    TransferRequest transferRequest = new TransferRequest(1,-111,222,new BigDecimal(123.32));
    TransferController transferController = new TransferController(transferService);

    // When
    Response response = transferController.transfer(transferRequest);

    // Then
    assertNotNull(response);
    verify(transferService, times(0)).transfer(any());
  }
}