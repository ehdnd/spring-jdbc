package io.github.ehdnd.jdbc.connection;

import static io.github.ehdnd.jdbc.connection.ConnectionConst.PASSWORD;
import static io.github.ehdnd.jdbc.connection.ConnectionConst.URL;
import static io.github.ehdnd.jdbc.connection.ConnectionConst.USERNAME;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DBConnectionUtil {

  public static Connection getConnection() {
    try {
      Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      log.info("get connection={}, class={}", connection, connection.getClass());
      return connection;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IllegalStateException(e);
    }
  }
}
