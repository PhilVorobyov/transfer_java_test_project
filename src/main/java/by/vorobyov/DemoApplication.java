package by.vorobyov;

import by.vorobyov.dao.TransferDao;
import by.vorobyov.service.TransferService;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class DemoApplication extends Application<DemoConfiguration> {

  public static void main(String[] args) throws Exception {
    new DemoApplication().run("server", "config.yml");
  }

  @Override
  public void run(DemoConfiguration configuration, Environment environment) {
    final DBIFactory factory = new DBIFactory();
    final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
    final TransferDao transferDao = jdbi.onDemand(TransferDao.class);
    final TransferService transferService = new TransferService(transferDao);
    TransferController transferController = new TransferController(transferService);
    environment.jersey().register(transferService);
  }
}