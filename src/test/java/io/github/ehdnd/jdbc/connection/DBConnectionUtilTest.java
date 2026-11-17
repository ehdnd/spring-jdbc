package io.github.ehdnd.jdbc.connection;


import static org.assertj.core.api.Assertions.*;

import java.sql.Connection;
import org.junit.jupiter.api.Test;

class DBConnectionUtilTest {

  @Test
  void getConnection() {

    Connection connection = DBConnectionUtil.getConnection();
    assertThat(connection).isNotNull();
  }
}