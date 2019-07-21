package by.vorobyov.transfer;

import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.vorobyov.transfer.domain.TransferRequest;
import by.vorobyov.transfer.service.TransferService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import javax.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class TransferControllerTest {

  @Mock
  TransferService transferService;

  @InjectMocks
  TransferController transferController;

  @Test
  public void transfer_testSuccessfullTransfer() {
    // Given
    TransferRequest transferRequest = new TransferRequest(1,111,222,new BigDecimal(123.32));
    Response res = Response.status(OK).build();
    when(transferService.transfer(transferRequest)).thenReturn(res);

    // When
    Response response = transferController.transfer(transferRequest);

    // Then
    assertNotNull(response);
    assertEquals(OK, response.getStatusInfo());
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