## setup H2

인텔리제이에서:

H2 서버 실행 후
Database 탭
-> 추가
-> DataSource 에서 H2 선택
-> database 칸에 ~/jdbc 작성
-> Drives 에서 H2 - Driver Files 에서 `+`
-> `~/Documents/inflearn/h2/bin/h2-2.4.240.jar` 선택

## H2 실행 및 연결, 종료

ssh 연결 이후 터미널 창 하나에서

서버가 켜져있는지 확인:

```bash
lsof -nP -iTCP:9092 -sTCP:LISTEN
```

서버 켜기:

```bash
cd ~/Documents/inflearn/h2/bin
java -cp h2-2.4.240.jar org.h2.tools.Server -tcp
```

인텔리제이로 보기.

서버 끄기:
ctrl + C

or

```bash
java -cp ~/Documents/inflearn/h2/bin/h2-2.4.240.jar org.h2.tools.Server -tcpShutdown tcp://localhost:9092
```

or

```bash
kill <PID>
```

## 기타 메모

- `console [jdbc@localhost]` 창: 임시 SQL 입력 창
- `schema.sql`: 프로젝트에 저장되는 파일, 다시 쓸 SQL
