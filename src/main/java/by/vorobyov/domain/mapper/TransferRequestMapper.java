package by.vorobyov.domain.mapper;

import by.vorobyov.domain.TransferRequest;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransferRequestMapper implements ResultSetMapper<TransferRequest>
{
  public TransferRequest map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException
  {
    return new TransferRequest(resultSet.getString("ID"), resultSet.getString("NAME"), resultSet.getString("NAME"), new BigDecimal(0));
  }
}
