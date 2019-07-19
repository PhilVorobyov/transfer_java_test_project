package by.vorobyov.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class TransferRequest {

  @NotEmpty private String userId;
  @NotEmpty private String accountOrigin;
  @NotEmpty private String accountDestination;
  @NotNull private BigDecimal amount;

  public TransferRequest() {
  }

  public TransferRequest(final String userId, final String accountOrigin, final String accountDestination, final BigDecimal amount) {
    this.userId = userId;
    this.accountOrigin = accountOrigin;
    this.accountDestination = accountDestination;
    this.amount = amount;
  }
}
