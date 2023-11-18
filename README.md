### 사용 기술

솔직히 이해가 안되는 기술 부분이 정말 많습니다. 일정 맞춘다고 급하게 깃허브나 다른 곳에서 복붙했던 코드들도 많아서요 ㅜㅜ

#### 백엔드
- **Spring Boot**: 애플리케이션의 기본 프레임워크로 사용되며, 의존성 관리와 자동 구성을 통해 개발 속도를 향상시킵니다.
- **Spring Security**: 인증 및 권한 부여를 위한 보안 프레임워크로 사용됩니다.
- **Spring Data JPA**: 데이터베이스와의 상호작용을 추상화하고, 객체 관계 매핑(ORM)을 제공합니다.
- **Spring Data Redis**: 캐싱 및 세션 관리를 위해 Redis와의 통합을 제공합니다.
- **Spring WebFlux**: 비동기 및 논블로킹 I/O를 위한 반응형 프로그래밍을 지원합니다.
- **JJWT**: JSON Web Token을 생성하고 검증하기 위한 라이브러리입니다.
- **LINODE S3**: 파일 저장 및 관리를 위한 클라우드 스토리지 서비스입니다.
- **Jedis**: Java 어플리케이션에서 Redis 클라이언트로 사용됩니다.

#### 데이터베이스
- **MySQL**: 관계형 데이터베이스 관리 시스템(RDBMS)으로 사용됩니다.

#### 인프라
- **Redis**: 캐싱 및 메시지 브로커로 사용됩니다.

#### 빌드 및 배포
- **Maven**: 프로젝트의 빌드 및 의존성 관리를 위한 도구입니다.
- **LINODE**: 애플리케이션 호스팅 및 NGINX를 통한 웹 서버 관리

### 성능적/설계적 핵심 포인트 

#### 성능적 포인트

1. **Redis 캐싱**:
   - 세션 관리, 장바구니 정보, 자주 조회되는 데이터 등을 Redis에 캐싱하여 빠른 데이터 검색과 응답 시간 단축을 실현합니다.
   - Redis의 빠른 읽기/쓰기 속도를 활용하여 성능을 최적화합니다.

2. **비동기 처리(WebFlux)**:
   - Spring WebFlux를 사용하여 서버의 리소스를 효율적으로 사용하고, 높은 동시성을 처리할 수 있도록 설계합니다.
   - 논블로킹 I/O를 통해 시스템의 전체적인 처리량과 성능을 향상시킵니다.

3. **JWT 토큰**:
   - 사용자 인증 정보를 서버가 아닌 클라이언트 측에서 JWT 토큰으로 관리하여 서버의 부하를 줄이고, 확장성을 높입니다.
   - 토큰 기반 인증을 통해 RESTful API의 상태 비저장(Stateless) 특성을 유지하며 보안성을 강화합니다.

#### 설계적 포인트

1. **보안**:
   - Spring Security를 통해 사용자 인증 및 권한 부여를 철저히 관리합니다.
   - 비밀번호는 bcrypt 해시 알고리즘을 사용하여 암호화하여 저장함으로써 보안성을 강화합니다.
2. **RESTful API**:
   - 클라이언트와 서버 간의 통신을 위해 REST 원칙을 따르는 API를 설계하여 확장성과 유지보수성을 높이고, API 문서화를 통해 개발자 간의 원활한 협업을 지원합니다.
3. **MVC 패턴**:
   - 컨트롤러(Controller), 서비스(Service), 리포지토리(Repository)의 분리를 통해 관심사의 분리(SoC)를 적용하고, 코드의 가독성과 유지보수성을 향상시킵니다.
5. **클라우드 스토리지(AWS S3)**:
   - 파일을 안전하게 저장하고 관리하기 위해 AWS S3를 사용합니다. 이를 통해 서버의 스토리지 부담을 줄이고, 데이터의 안정성을 보장합니다.
6. **코드 기반 구성**:
   - 애플리케이션의 설정을 코드로 관리하여 환경에 따른 설정 변경을 용이하게 하고, 버전 관리 시스템을 통해 변경 사항을 추적합니다.
