package io.github.ehdnd.jdbc.repository;


import static io.github.ehdnd.jdbc.connection.ConnectionConst.PASSWORD;
import static io.github.ehdnd.jdbc.connection.ConnectionConst.URL;
import static io.github.ehdnd.jdbc.connection.ConnectionConst.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.zaxxer.hikari.HikariDataSource;
import io.github.ehdnd.jdbc.connection.ConnectionConst;
import io.github.ehdnd.jdbc.domain.Member;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.runners.DefaultInternalRunner;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Slf4j
class MemberRepositoryV1Test {

  // DI -> 코드의 변경이 전혀 필요 없다. DataSource 사용의 장점이다.
  MemberRepositoryV1 repository;

  @BeforeEach
  void beforeEach() {
    // 기본 DriverManager - 항상 새로운 커넥션을 획득
//    DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

    // 커넥션 풀링 -> close 하기에 con1 커넥션이 재사용된다
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(URL);
    dataSource.setUsername(USERNAME);
    dataSource.setPassword(PASSWORD);

    repository = new MemberRepositoryV1(dataSource);
  }

  @Test
  void crud() throws SQLException {
    // save
    Member member = new Member("memberV0", 10000);
    repository.save(member);

    // findById
    Member findMember = repository.findById(member.getMemberId());
    log.info("findMember={}", findMember);

    assertThat(findMember).isEqualTo(member);

    // update: money 10000 -> 20000
    repository.update(member.getMemberId(), 20000);
    Member updateMember = repository.findById(member.getMemberId());
    assertThat(updateMember.getMoney()).isEqualTo(20000);

    // delete
    repository.delete(member.getMemberId());
    assertThatThrownBy(() -> repository.findById(member.getMemberId())).isInstanceOf(
        NoSuchElementException.class);

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}