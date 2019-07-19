package by.vorobyov.dao;

import by.vorobyov.domain.Account;
import by.vorobyov.domain.mapper.AccountMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.math.BigDecimal;
import java.util.List;

@RegisterMapper(AccountMapper.class)
public interface TransferDao {

  @SqlQuery("select * from ACCOUNT")
  List<Account> getAll();

  @SqlQuery("select * from ACCOUNT where account_id = :account_id")
  Account getAccountById(@Bind("account_id") int account_id);

  @SqlUpdate("update contact set firstName = :firstName, lastName = :lastName, phone = :phone where id = :id")
  void updateAccount(@Bind("account_id") int account_id, @Bind("user_id") int user_id, @Bind("lastName") String lastName, @Bind("amount") BigDecimal amount);

  @SqlUpdate("CREATE TABLE IF NOT EXISTS Employees (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL, phoneNumber VARCHAR(255) NOT NULL, acronym VARCHAR(255) NOT NULL, primary key(name, acronym))")
  void createEmployeeTable();

}
