package by.vorobyov.transfer.dao;

import by.vorobyov.transfer.domain.Account;
import by.vorobyov.transfer.domain.mapper.AccountMapper;
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

  @SqlQuery("select * from ACCOUNT where account_number = :account_number")
  Account getAccountByNumber(@Bind("account_number") int account_number);

  @SqlUpdate("update ACCOUNT set amount = :amount where account_number = :account_number")
  void updateAccount(@Bind("account_number") int account_number, @Bind("amount") BigDecimal amount);

}
