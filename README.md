# prodapp

Java Socket(ServerSocket) 기반 JSON 상품 관리 서버/클라이언트 프로젝트

---

## 프로젝트 구조

```
prodapp
 ├─ client
 │   └─ MyClient.java
 ├─ dto
 │   ├─ RequestDTO.java
 │   └─ ResponseDTO.java
 └─ server
     ├─ DBConnection.java
     ├─ MyServer.java
     ├─ Product.java
     ├─ ProductRepository.java
     ├─ ProductService.java
     └─ ProductServiceInterface.java
```

---

## 실행 방식

- 서버는 20000번 포트에서 대기
- 클라이언트 연결 후 소켓 유지
- JSON 요청 1건 처리 → JSON 응답 1건 반환
- 요청과 응답은 한 줄(JSON) 기준

---

## 클라이언트 (MyClient)

- 서버(localhost:20000)에 Socket 연결
- 키보드 입력(JSON 문자열)을 서버로 전송
- 서버 응답(JSON)을 출력
- while(true)로 반복 통신

---

## 요청 DTO (RequestDTO)

- method : 요청 방식 (get, post, delete)
- querystring : 조회/삭제 시 id 전달
- body : 상품 등록 시 사용

```json
{
  "method":"get",
  "querystring":null,
  "body":null
}
```

---

## 응답 DTO (ResponseDTO)

- msg : "ok" 또는 예외 메시지
- body : 객체 / 리스트 / null

```json
{
  "msg":"ok",
  "body":null
}
```

---

## 서버 (MyServer)

- ServerSocket 생성 후 accept()
- 클라이언트 요청을 while(true)로 처리
- Gson으로 JSON 파싱
- 요청 종류에 따라 서비스 호출
- ResponseDTO를 JSON으로 변환 후 응답

---

## 요청 분기 기준

| method | querystring | 처리 내용 |
| ------ | ----------- | -------- |
| post   | null        | 상품 등록 |
| get    | null        | 상품 목록 |
| get    | not null    | 상품 상세 |
| delete | not null    | 상품 삭제 |

---

## Product (Entity)

- id
- name
- price
- qty

---

## Service 계층

### ProductServiceInterface

- 상품등록
- 상품목록
- 상품상세
- 상품삭제

### ProductService

- Repository 호출
- 상품 상세 조회 결과가 없으면 예외 발생

---

## Repository 계층 (ProductRepository)

- insert
- deleteById
- findById
- findAll

DB 조회 결과를 Product 객체로 변환하여 반환

---

## DBConnection

- MySQL JDBC 드라이버 로딩
- DB Connection 생성 후 반환

---

## 테스트 요청 예시

### 상품 목록

```json
{"method":"get","querystring":null,"body":null}
```

### 상품 상세

```json
{"method":"get","querystring":{"id":1},"body":null}
```

### 상품 등록

```json
{"method":"post","querystring":null,"body":{"name":"바나나","price":1000,"qty":10}}
```

### 상품 삭제

```json
{"method":"delete","querystring":{"id":1},"body":null}
```

---

## 특징

- 단일 클라이언트 기준 서버
- 연결 유지 기반 반복 요청 처리
- 예외 메시지를 JSON 응답으로 전달
- 학습 목적의 간단한 구조
