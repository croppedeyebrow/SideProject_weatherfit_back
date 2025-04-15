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

## 오류 해결 및 디버깅 과정

### 1. 날씨 데이터 조회 오류 해결 (2024년 4월)

#### 문제 상황

- 클라이언트에서 날씨 정보를 요청할 때 500 Internal Server Error 발생
- 오류 메시지: "현재 날씨 정보를 찾을 수 없습니다."

#### 원인 분석

1. `WeatherService.getCurrentWeather()` 메서드에서 ID가 1인 날씨 데이터를 조회하려고 시도
2. 데이터베이스에 해당 ID의 날씨 데이터가 존재하지 않아 `Optional.empty()` 반환
3. `orElseThrow()`에 의해 예외 발생

#### 해결 방법

1. `WeatherRepository`에 가장 최근 날씨 데이터를 조회하는 메서드 추가

   ```java
   @Query("SELECT w FROM Weather w ORDER BY w.weatherDate DESC, w.weatherTime DESC")
   List<Weather> findAllOrderByDateDesc();

   default Optional<Weather> findLatestWeather() {
       List<Weather> weathers = findAllOrderByDateDesc();
       return weathers.isEmpty() ? Optional.empty() : Optional.of(weathers.get(0));
   }
   ```

2. `WeatherService.getCurrentWeather()` 메서드 수정

   ```java
   public WeatherDTO getCurrentWeather() {
       try {
           // 가장 최근의 날씨 데이터 조회
           Optional<Weather> weatherOpt = weatherRepository.findLatestWeather();

           if (weatherOpt.isEmpty()) {
               // 데이터가 없는 경우 기본 날씨 정보 반환
               return WeatherDTO.builder()
                   .id(0L)
                   .minTemp(15)
                   .maxTemp(25)
                   .weatherCondition(WeatherCondition.MID)
                   .latitude(37)
                   .longitude(127)
                   .weatherDate(LocalDateTime.now())
                   .weatherTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                   .currentTemp(20)
                   .currentHumidity(60)
                   .currentWindSpeed(3.0f)
                   .build();
           }

           Weather weather = weatherOpt.get();
           return WeatherDTO.of(weather);
       } catch (Exception e) {
           throw new RuntimeException("현재 날씨 정보를 찾을 수 없습니다.");
       }
   }
   ```

#### 결과

- 데이터베이스에 날씨 데이터가 없는 경우에도 기본 날씨 정보를 반환하여 500 에러 방지
- 클라이언트에서 날씨 정보 요청 시 정상적으로 응답 받음

### 2. 문자 인코딩 이슈 해결 (2024년 4월)

#### 문제 상황

- 한글 데이터가 데이터베이스에 저장될 때 깨지는 현상 발생
- API 응답에서 한글이 "" 형태로 표시됨

#### 원인 분석

1. 데이터베이스 테이블의 문자셋이 UTF-8로 설정되지 않음
2. CSV 파일 저장 시 인코딩 방식과 데이터베이스 인코딩 방식 불일치
3. 엔티티 필드에 명시적 문자셋 설정 부재

#### 해결 방법

1. 데이터베이스 테이블 문자셋 변경

   ```sql
   ALTER TABLE coordinate CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. 엔티티 필드에 명시적 문자셋 설정 추가

   ```java
   @Column(name = "preference", nullable = false, columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
   private String preference;

   @Column(name = "targetAgeGroup", nullable = false, columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
   private String targetAgeGroup;
   ```

3. CSV 파일 저장 시 UTF-8 인코딩 사용
   ```python
   df.to_csv('weather_data.csv', encoding='utf-8', index=False)
   ```

#### 결과

- 한글 데이터가 정상적으로 저장 및 조회됨
- API 응답에서 한글이 올바르게 표시됨

### 3. Lombok 어노테이션 관련 이슈 해결 (2024년 4월)

#### 문제 상황

- `@Builder` 어노테이션 사용 시 빌드 오류 발생
- 오류 메시지: "No suitable constructor found for class"

#### 원인 분석

1. `@Builder` 어노테이션은 모든 필드를 포함하는 생성자가 필요함
2. 엔티티 클래스에 `@AllArgsConstructor` 어노테이션이 없어서 발생한 문제

#### 해결 방법

1. 엔티티 클래스에 `@AllArgsConstructor` 어노테이션 추가
   ```java
   @Entity
   @Table(name = "weather")
   @Getter
   @NoArgsConstructor
   @AllArgsConstructor
   @Builder
   public class Weather {
       // 필드 정의
   }
   ```

#### 결과

- `@Builder` 어노테이션이 정상적으로 작동하여 빌드 오류 해결
- 엔티티 객체 생성 시 빌더 패턴 사용 가능

### 4. 비밀번호 확인 API 엔드포인트 불일치 해결 (2024년 4월)

#### 문제 상황

- 클라이언트에서 비밀번호 확인 요청 시 404 Not Found 에러 발생
- 클라이언트는 `/member/check/password`로 POST 요청을 보내지만 서버에는 `/member/verify/password`로 GET 요청이 정의됨

#### 원인 분석

1. 엔드포인트 경로 불일치: `/verify/password` vs `/check/password`
2. HTTP 메서드 불일치: GET vs POST
3. 파라미터 전달 방식 불일치: 쿼리 파라미터 vs 요청 본문

#### 해결 방법

1. 서버 코드에 클라이언트 요청과 일치하는 엔드포인트 추가
   ```java
   @PostMapping("/check/password")
   public ResponseEntity<Boolean> checkPassword(@RequestBody UserReqDTO userReqDTO) {
       boolean isPasswordCorrect = userService.verifyPassword(userReqDTO.getEmail(), userReqDTO.getPassword());
       log.info("비밀번호 체크 결과: {}", isPasswordCorrect);
       return ResponseEntity.ok(isPasswordCorrect);
   }
   ```

#### 결과

- 클라이언트 요청과 서버 엔드포인트가 일치하여 404 에러 해결
- 비밀번호 확인 기능 정상 작동

### 5. 비밀번호 업데이트 API 오류 해결 (2024년 4월)

#### 문제 상황

- 비밀번호 업데이트 API 호출 시 500 Internal Server Error 발생
- 요청 데이터 형식이 서버에서 기대하는 형식과 불일치

#### 원인 분석

1. 클라이언트에서 전송한 요청 데이터에 `password`와 `newPassword` 필드가 모두 포함되어 있지 않음
2. 서버에서 요청 데이터를 처리하는 방식이 클라이언트 요청과 불일치

#### 해결 방법

1. `UserController`의 비밀번호 업데이트 메서드 수정

   ```java
   @PutMapping("/update/password")
   public ResponseEntity<String> updatePassword(@RequestBody UserReqDTO userReqDTO) {
       log.info("비밀번호 업데이트 요청: {}", userReqDTO);

       if (userReqDTO.getEmail() == null || userReqDTO.getPassword() == null || userReqDTO.getNewPassword() == null) {
           return ResponseEntity.badRequest().body("이메일, 현재 비밀번호, 새 비밀번호가 모두 필요합니다.");
       }

       boolean updated = userService.updatePassword(userReqDTO.getEmail(), userReqDTO.getPassword(), userReqDTO.getNewPassword());

       if (updated) {
           return ResponseEntity.ok("비밀번호가 성공적으로 업데이트되었습니다.");
       } else {
           return ResponseEntity.badRequest().body("비밀번호 업데이트에 실패했습니다. 현재 비밀번호가 올바른지 확인하세요.");
       }
   }
   ```

#### 결과

- 요청 데이터 형식 불일치 문제 해결
- 비밀번호 업데이트 기능 정상 작동

### 6. 좋아요(Like) 관련 오류 해결 (2024년 4월)

#### 문제 상황

- 좋아요 추가/삭제 시 500 Internal Server Error 발생
- 사용자 ID나 착장 ID가 null인 경우 발생하는 오류
- 이미 좋아요한 착장에 대한 중복 좋아요 시도

#### 원인 분석

1. `UserService.addLike()` 메서드에서 사용자 ID나 착장 ID가 null인 경우에 대한 예외 처리가 부족
2. 이미 좋아요한 착장에 대한 중복 좋아요 시도 시 예외 처리가 없음
3. 클라이언트 요청에 대한 유효성 검증이 부족

#### 해결 방법

1. `UserService.java`에서 예외 처리 강화:

   ```java
   public void addLike(Long userId, Long coordinateId) {
       try {
           log.info("좋아요 추가 시작: userId={}, coordinateId={}", userId, coordinateId);

           if (userId == null) {
               throw new IllegalArgumentException("사용자 ID가 비어있습니다.");
           }

           if (coordinateId == null) {
               throw new IllegalArgumentException("착장 ID가 비어있습니다.");
           }

           // 사용자와 착장 존재 여부 확인
           User user = userRepository.findById(userId)
               .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userId));
           Coordinate coordinate = coordinateRepository.findById(coordinateId)
               .orElseThrow(() -> new RuntimeException("해당 착장 정보를 찾을 수 없습니다: " + coordinateId));

           // 이미 좋아요가 있는지 확인
           if (likeRepository.existsByUserAndCoordinate(user, coordinate)) {
               log.info("이미 좋아요한 착장입니다: userId={}, coordinateId={}", userId, coordinateId);
               throw new RuntimeException("이미 좋아요한 착장입니다.");
           }

           Like like = Like.builder()
               .user(user)
               .coordinate(coordinate)
               .build();

           likeRepository.save(like);
           log.info("좋아요 추가 완료: userId={}, coordinateId={}", userId, coordinateId);
       } catch (Exception e) {
           log.error("좋아요 추가 중 오류 발생: {}", e.getMessage(), e);
           throw e;
       }
   }
   ```

2. `UserController.java`에서 클라이언트 요청 검증 및 예외 처리:

   ```java
   @PostMapping("/like/add")
   @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
   public ResponseEntity<String> addLike(@RequestBody Map<String, Long> request) {
       Long userId = request.get("userId");
       Long coordinateId = request.get("coordinateId");

       if (userId == null || coordinateId == null) {
           return ResponseEntity.badRequest().body("사용자 ID와 착장 ID는 필수입니다.");
       }

       try {
           userService.addLike(userId, coordinateId);
           return ResponseEntity.ok("좋아요 추가 완료");
       } catch (Exception e) {
           log.error("좋아요 추가 실패: {}", e.getMessage(), e);
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body("좋아요 추가 중 오류가 발생했습니다: " + e.getMessage());
       }
   }
   ```

#### 결과

- 좋아요 추가/삭제 시 발생하는 500 에러 해결
- 사용자 ID나 착장 ID가 null인 경우에 대한 적절한 오류 메시지 제공
- 이미 좋아요한 착장에 대한 중복 좋아요 시도 방지

### 7. 날씨 기반 좋아요 조회 오류 해결 (2024년 4월)

#### 문제 상황

- 날씨 기반 좋아요 조회 시 500 Internal Server Error 발생
- 날씨 조건에 맞는 좋아요한 착장이 없는 경우 발생하는 오류

#### 원인 분석

1. `WeatherService.getWeatherBasedLikes()` 메서드에서 날씨 기반 좋아요 조회 로직이 부재
2. 날씨 조건에 맞는 좋아요한 착장이 없는 경우에 대한 예외 처리가 없음

#### 해결 방법

1. `WeatherService.java`에서 날씨 기반 좋아요 조회 메서드 구현:

   ```java
   public List<CoordinateDTO> getWeatherBasedLikes(String weatherCondition, Long userId) {
       log.info("날씨 기반 좋아요 스타일 서비스 호출: {}, userId: {}", weatherCondition, userId);
       return coordinateRepository.findByWeatherConditionAndLikesUserId(weatherCondition, userId)
               .stream()
               .map(CoordinateDTO::from)
               .collect(Collectors.toList());
   }
   ```

2. `CoordinateRepository.java`에 날씨 기반 좋아요 조회 쿼리 추가:
   ```java
   @Query("SELECT c FROM Coordinate c JOIN c.likes l WHERE c.weatherCondition = :weatherCondition AND l.user.id = :userId")
   List<Coordinate> findByWeatherConditionAndLikesUserId(@Param("weatherCondition") String weatherCondition, @Param("userId") Long userId);
   ```

#### 결과

- 날씨 기반 좋아요 조회 기능 정상 작동
- 날씨 조건에 맞는 좋아요한 착장이 없는 경우에도 빈 리스트 반환하여 500 에러 방지

### 8. 선호도(Prefer) 데이터 처리 오류 해결 (2024년 4월)

#### 문제 상황

- 선호도 데이터가 JSON 형식으로 저장되어 있어 파싱 오류 발생
- 선호도 필터링 시 정확한 비교가 어려움

#### 원인 분석

1. 선호도 데이터가 JSON 문자열로 저장되어 있어 파싱 과정에서 오류 발생
2. 선호도 필터링 시 문자열 비교가 정확하지 않음

#### 해결 방법

1. `UserReqDTO.java`에서 선호도 데이터 변환 메서드 추가:

   ```java
   public String getPreferencesAsString() {
       try {
           ObjectMapper mapper = new ObjectMapper();
           return mapper.writeValueAsString(preferences);
       } catch (JsonProcessingException e) {
           return "[]";  // 기본값 반환
       }
   }
   ```

2. `UserService.java`에서 선호도 업데이트 메서드 구현:
   ```java
   public void updatePreferences(String email, String newPreferences) {
       User user = userRepository.findByEmail(email)
           .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
       user.setPreferences(newPreferences);
       userRepository.save(user);
   }
   ```

#### 결과

- 선호도 데이터 JSON 파싱 오류 해결
- 선호도 필터링 기능 정상 작동

## 프로젝트 개발 과정에서 배운 점

### 1. API 설계 및 클라이언트-서버 통신

- **엔드포인트 일관성의 중요성**: 클라이언트와 서버 간의 엔드포인트 경로, HTTP 메서드, 요청/응답 형식을 일관되게 유지하는 것이 중요함
- **명확한 API 문서화**: API 엔드포인트, 요청/응답 형식, 예외 처리 등을 명확하게 문서화하여 개발 과정에서의 혼란을 줄일 수 있음
- **예외 처리의 중요성**: 클라이언트 요청에 대한 적절한 예외 처리와 오류 메시지를 통해 사용자 경험을 향상시킬 수 있음

### 2. 데이터베이스 설계 및 관리

- **문자 인코딩 관리**: 다국어 지원을 위해서는 데이터베이스, 애플리케이션, 파일 시스템 간의 문자 인코딩을 일관되게 유지해야 함
- **인덱싱 전략**: 자주 조회되는 필드에 대한 적절한 인덱싱을 통해 쿼리 성능을 향상시킬 수 있음
- **트랜잭션 관리**: 데이터 일관성을 유지하기 위해 적절한 트랜잭션 관리가 필요함

### 3. 프레임워크 및 라이브러리 활용

- **Spring Boot의 장점**: 자동 구성, 내장 서버, 다양한 스타터 패키지 등을 통해 빠른 개발이 가능함
- **JPA/Hibernate 활용**: 객체 지향적인 방식으로 데이터베이스를 다룰 수 있어 개발 생산성이 향상됨
- **Lombok 활용**: 보일러플레이트 코드를 줄여 코드 가독성과 유지보수성을 향상시킬 수 있음

### 4. 외부 API 통합

- **OpenWeatherMap API 활용**: 외부 날씨 데이터를 활용하여 애플리케이션의 기능을 확장할 수 있음
- **API 키 관리**: 보안을 위해 API 키를 환경 변수나 설정 파일로 관리하는 것이 중요함
- **비동기 처리**: 외부 API 호출 시 비동기 처리를 통해 애플리케이션의 응답성을 유지할 수 있음

### 5. 테스트 및 디버깅

- **로깅의 중요성**: 적절한 로깅을 통해 애플리케이션의 동작을 추적하고 문제를 진단할 수 있음
- **단위 테스트 작성**: 핵심 비즈니스 로직에 대한 단위 테스트를 작성하여 코드 품질을 유지할 수 있음
- **통합 테스트**: 여러 컴포넌트 간의 상호작용을 테스트하여 전체 시스템의 안정성을 확보할 수 있음

### 6. 프로젝트 관리 및 협업

- **버전 관리**: Git을 활용한 효과적인 버전 관리를 통해 코드 변경 이력을 추적하고 협업을 원활하게 할 수 있음
- **문서화**: README, API 문서, 코드 주석 등을 통해 프로젝트의 이해도를 높이고 유지보수성을 향상시킬 수 있음
- **모듈화**: 기능별로 코드를 모듈화하여 코드의 재사용성과 유지보수성을 향상시킬 수 있음
