package by.vorobyov.transfer.domain.mapper;

import by.vorobyov.transfer.domain.Account;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements ResultSetMapper<Account>
{
  public Account map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException
  {
    return new Account(resultSet.getInt("account_number"), resultSet.getInt("user_id"), resultSet.getBigDecimal("amount"));
  }
}
