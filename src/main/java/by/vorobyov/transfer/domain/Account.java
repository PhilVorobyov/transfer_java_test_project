package by.vorobyov.transfer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Account {

  @NotEmpty  private int account_number;
  @NotEmpty private int user_id;
  @NotNull private BigDecimal amount;
}
