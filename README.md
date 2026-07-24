# study

Java 21 / Maven 기본 프로젝트.

## 요구사항

- JDK 21 (`JAVA_HOME` 설정됨: Corretto 21.0.6)
- Maven 3.9+

## 사용법

```bash
mvn compile                 # 컴파일
mvn test                    # 테스트
mvn exec:java               # 실행
mvn exec:java -Dexec.args="철수"   # 인자 전달 실행
mvn package                 # 실행 가능한 jar 생성
java -jar target/study-1.0-SNAPSHOT.jar
```

## 구조

```
pom.xml
src/main/java/com/example/study/App.java
src/main/java/com/example/study/practice/   # 알고리즘 연습 파일 (각각 main 보유)
src/test/java/com/example/study/AppTest.java
```

## 연습 파일 실행

각 파일은 `com.example.study.practice` 패키지의 독립 실행 클래스입니다.

```bash
mvn compile
mvn exec:java -Dexec.mainClass=com.example.study.practice.st_0722_1
```

| 클래스 | 내용 |
| --- | --- |
| `st_0722_1` | 미로 탈출 최소 이동 횟수 (BFS, 0=이동 가능) |
| `st_0722_2` | S→T 최단 거리 (BFS, 1=이동 가능) |
| `st_0723_1` | 순열 전체 출력 (백트래킹) |
| `st_0723_2` | N개 중 M개 순열 |
| `st_0723_3` | 순열 + 인접 합 5 이하 조건 |
| `st_0723_4` | 부분합 7 만들기 |
| `st_0723_5` | 미로 최단 거리 |
| `st_0723_6` | `st_0723_5` 와 동일 로직 |
