# WeatherFit - 날씨 기반 스타일 추천 서비스

## 프로젝트 개발 진행 내역

### 1. 프로젝트 초기 설정 (2024년 1월)

- Spring Boot 프로젝트 생성
- 기본 디렉토리 구조 설정
- MySQL 데이터베이스 연결 설정
- 기본 의존성 추가 (Spring Web, Spring Data JPA, MySQL Connector, Lombok 등)

### 2. 기본 엔티티 설계 (2024년 1월 ~ 2월 초)

- `User` 엔티티: 사용자 정보 관리
- `Prefer` 엔티티: 사용자 선호도 정보 관리
- `Coordinate` 엔티티: 날씨 기반 스타일 정보 관리
- `Like` 엔티티: 사용자별 스타일 좋아요 정보 관리

### 3. 날씨 데이터 모델 설계 (2024년 2월)

- `WeatherCondition` 열거형: 날씨 상태 정의 (HOT, WARM, MID, COLD, CHILL, RAIN, SNOW)
- `Weather` 엔티티: 현재 날씨 정보 저장
- `WeatherForecast` 엔티티: 날씨 예보 정보 저장
- 날씨 데이터 수집을 위한 Python 스크립트 개발 (OpenWeatherMap API 활용)

### 4. 리포지토리 계층 구현 (2024년 2월)

- `UserRepository`: 사용자 정보 조회 및 관리
- `CoordinateRepository`: 스타일 정보 조회 및 관리
- `LikeRepository`: 좋아요 정보 조회 및 관리
- `WeatherRepository`: 현재 날씨 정보 조회
- `WeatherForecastRepository`: 날씨 예보 정보 조회

### 5. 서비스 계층 구현 (2024년 2월 ~ 3월)

- `UserService`: 사용자 관리 기능 구현
  - 회원가입, 로그인, 비밀번호 변경, 이메일 인증 등
- `AuthService`: 인증 관련 기능 구현
- `EmailService`: 이메일 발송 기능 구현
- `CoordinateService`: 스타일 추천 기능 구현
- `WeatherService`: 날씨 정보 조회 및 날씨 기반 스타일 추천 기능 구현

### 6. 컨트롤러 계층 구현 (2024년 2월 ~ 3월)

- `UserController`: 사용자 관련 API 엔드포인트 구현
- `AuthController`: 인증 관련 API 엔드포인트 구현
- `CoordinateController`: 스타일 관련 API 엔드포인트 구현
- `WeatherController`: 날씨 관련 API 엔드포인트 구현

### 7. DTO 계층 구현 (2024년 2월 ~ 3월)

- `UserReqDTO`, `UserResDTO`: 사용자 요청/응답 데이터 전송 객체
- `CoordinateDTO`: 스타일 정보 전송 객체
- `LikeDTO`: 좋아요 정보 전송 객체
- `WeatherDTO`: 현재 날씨 정보 전송 객체
- `WeatherForecastDTO`: 날씨 예보 정보 전송 객체

### 8. 날씨 기반 스타일 추천 기능 구현 (2024년 4월)

- 날씨 조건별 스타일 추천 API 구현
- 사용자별 좋아요한 스타일 조회 API 구현
- 스타일 좋아요 토글 API 구현

### 9. 문자 인코딩 이슈 해결 (2024년 4월)

- 한글 데이터 저장 및 조회 시 발생하는 인코딩 문제 해결
- 데이터베이스 테이블 문자셋을 utf8mb4로 변경
- 엔티티 필드에 명시적 문자셋 설정 추가

### 10. 빌드 및 배포 설정 (2024년 4월)

- Maven 빌드 설정 최적화
- Lombok 어노테이션 관련 이슈 해결

## 현재 구현된 주요 기능

### 1. 사용자 관리

- 회원가입 및 로그인
- 이메일 인증
- 비밀번호 변경 및 찾기
- 사용자 정보 수정

### 2. 날씨 정보 조회

- 현재 날씨 정보 조회
- 날씨 예보 정보 조회 (3시간 단위, 5일 예보)
- 날씨 상태 분류 (HOT, WARM, MID, COLD, CHILL, RAIN, SNOW)

### 3. 날씨 기반 스타일 추천

- 날씨 조건별 스타일 추천
- 스타일 상세 정보 조회
- 스타일 좋아요 기능

### 4. 데이터 수집 및 관리

- OpenWeatherMap API를 통한 날씨 데이터 수집
- Python 스크립트를 통한 자동 데이터 수집 (1시간 간격)
- MySQL 데이터베이스를 통한 데이터 저장 및 관리
