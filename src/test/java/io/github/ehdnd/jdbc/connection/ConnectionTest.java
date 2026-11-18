package io.github.ehdnd.jdbc.connection;

import static io.github.ehdnd.jdbc.connection.ConnectionConst.PASSWORD;
import static io.github.ehdnd.jdbc.connection.ConnectionConst.URL;
import static io.github.ehdnd.jdbc.connection.ConnectionConst.USERNAME;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Slf4j
public class ConnectionTest {

  @Test
  void driverManager() throws SQLException {
    // 서로 다른 커넥션을 가지고 온다. 항상 새로운 커넥션을 생성한다.
    Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    log.info("connection={}, class={}", con1, con1.getClass());
    log.info("connection={}, class={}", con2, con2.getClass());
  }

  @Test
  void dataSourceDriverManager() throws SQLException {
    // DriverMangerDataSource - 항상 새로운 커넥션을 생성한다.
    DataSource dataSource = new DriverManagerDataSource(URL, USERNAME,
        PASSWORD);
    useDataSource(dataSource);
  }

  @Test
  void dataSourceConnectionPool() throws SQLException, InterruptedException {
    // 커넥션 풀링 (HikariCP): HikariProxyConnection(Proxy) -> JdbcConnection(Target)
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(URL);
    dataSource.setUsername(USERNAME);
    dataSource.setPassword(PASSWORD);
    dataSource.setMaximumPoolSize(10);
    dataSource.setPoolName("MyPool");

    useDataSource(dataSource);
    Thread.sleep(1000);
  }

  private void useDataSource(DataSource dataSource) throws SQLException {
    // 사용할 때는 파라미터를 전달하지 않아도 된다.
    // -> '설정과 사용의 분리' -> 리포지토리는 DataSource에만 의존하고 속성을 몰라도 된다.
    Connection con1 = dataSource.getConnection();
    Connection con2 = dataSource.getConnection();
    log.info("connection={}, class={}", con1, con1.getClass());
    log.info("connection={}, class={}", con2, con2.getClass());
  }
}
