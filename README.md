## Flow 파일 확장자 차단

### 배포된 웹사이트

[https://flow.seong-hun.tech](https://flow.seong-hun.tech/)


### API 명세 - Swagger

[https://flow.seong-hun.tech/swagger](https://flow.seong-hun.tech/swagger)


### 실행방법
1. Docker Compose를 통해서 MySQL을 실행시킵니다.

```bash
docker-compose up -d
```

2. `./gradlew build` 를 통해서, Spring Boot 프로젝트를 빌드합니다.

```bash
./gradlew build
```

3. build/libs 경로에 있는 jar 파일을 실행합니다.
```bash
java -jar build/libs/ship-0.0.1-SNAPSHOT.jar
```


## 테스트

### 테스트 방법
서버를 한번 시작시킨 뒤(MySQL 최초 스크립트 실행용입니다.)

`src/main/test/java/org/example/flow/domain/extension/ExtensionIntegrationTest.java` 

파일을 실행시키면 됩니다.

### 테스트 결과

<img width="674" alt="screenshot 2024-06-02 오후 7 22 37" src="https://github.com/seonghun-dev/flow/assets/80201773/e54bed91-4540-449f-bc36-ad04ad123964">
