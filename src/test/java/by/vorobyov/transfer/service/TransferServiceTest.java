package by.vorobyov.transfer.service;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import by.vorobyov.transfer.dao.TransferDao;
import by.vorobyov.transfer.domain.Account;
import by.vorobyov.transfer.domain.TransferRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.TransactionCallback;
import org.skife.jdbi.v2.TransactionStatus;
import org.skife.jdbi.v2.tweak.ConnectionFactory;

import java.math.BigDecimal;
import javax.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceTest {

  @Mock
  private Handle handle;

  @Mock
  private TransferDao transferDao;

  @Spy
  private DBI dbi = new DBI(mock(ConnectionFactory.class));

  @InjectMocks
  private TransferService transferService;

  @Before
  public void setUpDbi() {
    Mockito.doReturn(handle).when(dbi).open();
    Mockito.when(handle.attach(TransferDao.class)).thenReturn(transferDao);

    TransactionStatus status = Mockito.mock(TransactionStatus.class);

    Answer<Object> transactionalAnswer = invocation ->
        ((TransactionCallback) invocation.getArguments()[0]).inTransaction(handle, status);

    Mockito.when(handle.inTransaction(any())).thenAnswer(transactionalAnswer);
  }

  @Test
  public void transfer_success() {
    // Given
    TransferRequest transferRequest = new TransferRequest(1,111,222,new BigDecimal(50));
    Account account = new Account(1111, 1, new BigDecimal(100));
    when(transferDao.getAccountByNumber(transferRequest.getAccountOrigin())).thenReturn(account);
    when(transferDao.getAccountByNumber(transferRequest.getAccountDestination())).thenReturn(account);
    doNothing().when(transferDao).updateAccount(eq(account.getAccount_number()), any(BigDecimal.class));

    // When
    Response response = transferService.transfer(transferRequest);

    // Then
    assertNotNull(response);
    assertEquals(OK, response.getStatusInfo());
  }

  @Test
  public void transfer_incorrectOriginAccount() {
    // Given
    TransferRequest transferRequest = new TransferRequest(1,111,222,new BigDecimal(50));
    Account account = new Account(1111, 1, new BigDecimal(100));
    when(transferDao.getAccountByNumber(transferRequest.getAccountOrigin())).thenReturn(null);
    when(transferDao.getAccountByNumber(transferRequest.getAccountDestination())).thenReturn(account);
    doNothing().when(transferDao).updateAccount(eq(account.getAccount_number()), any(BigDecimal.class));

    // When
    Response response = transferService.transfer(transferRequest);

    // Then
    assertNotNull(response);
    assertEquals(BAD_REQUEST, response.getStatusInfo());
  }

  @Test
  public void transfer_incorrectDestinationAccount() {
    // Given
    TransferRequest transferRequest = new TransferRequest(1,111,222,new BigDecimal(50));
    Account account = new Account(1111, 1, new BigDecimal(100));
    when(transferDao.getAccountByNumber(transferRequest.getAccountOrigin())).thenReturn(account);
    when(transferDao.getAccountByNumber(transferRequest.getAccountDestination())).thenReturn(null);
    doNothing().when(transferDao).updateAccount(eq(account.getAccount_number()), any(BigDecimal.class));

    // When
    Response response = transferService.transfer(transferRequest);

    // Then
    assertNotNull(response);
    assertEquals(BAD_REQUEST, response.getStatusInfo());
  }

  @Test
  public void transfer_notEnoughBalance() {
    // Given
    TransferRequest transferRequest = new TransferRequest(1,111,222,new BigDecimal(50000));
    Account account = new Account(1111, 1, new BigDecimal(100));
    when(transferDao.getAccountByNumber(transferRequest.getAccountOrigin())).thenReturn(account);
    when(transferDao.getAccountByNumber(transferRequest.getAccountDestination())).thenReturn(account);
    doNothing().when(transferDao).updateAccount(eq(account.getAccount_number()), any(BigDecimal.class));

    // When
    Response response = transferService.transfer(transferRequest);

    // Then
    assertNotNull(response);
    assertEquals(BAD_REQUEST, response.getStatusInfo());
  }
}