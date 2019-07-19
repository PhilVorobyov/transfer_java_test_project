package by.vorobyov.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Account {

  @NotEmpty  private int account_id;
  @NotEmpty private int user_id;
  @NotNull private BigDecimal amount;
}
