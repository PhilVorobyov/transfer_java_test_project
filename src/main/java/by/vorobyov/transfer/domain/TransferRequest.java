package by.vorobyov.transfer.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class TransferRequest {

  @NotNull private int userId;
  @NotNull private int accountOrigin;
  @NotNull private int accountDestination;
  @NotNull private BigDecimal amount;

  public TransferRequest() {
  }

  public TransferRequest(final int userId, final int accountOrigin, final int accountDestination, final BigDecimal amount) {
    this.userId = userId;
    this.accountOrigin = accountOrigin;
    this.accountDestination = accountDestination;
    this.amount = amount;
  }
}
