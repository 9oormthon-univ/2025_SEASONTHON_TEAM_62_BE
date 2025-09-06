# 🏃‍♀️ SeasonThon - 안전한 러닝 경로 추천 플랫폼

> **2025 SEASONTHON TEAM 62 - Backend Repository**

## 📋 프로젝트 개요

SeasonThon은 사용자가 안전하고 최적화된 러닝 경로를 찾을 수 있도록 도와주는 플랫폼입니다. AI 기반 경로 추천, 실시간 안전 점수 분석, 크루 모집 기능을 제공하여 안전하고 즐거운 러닝 문화를 조성합니다.

### 🎯 주요 기능

- **🗺️ AI 기반 경로 추천**: 안전 점수를 고려한 최적의 러닝 경로 제안
- **👥 크루 시스템**: 함께 달릴 러닝 크루 생성 및 참여
- **⭐ 즐겨찾기**: 자주 사용하는 경로 저장 및 관리
- **📊 러닝 통계**: 개인 러닝 기록 및 성과 분석
- **🚨 신고 시스템**: 위험 요소 실시간 신고 및 관리
- **📱 최근 경로**: 완주한 경로 자동 저장 및 조회

## 🏗️ 기술 스택

### Backend
- **Framework**: Spring Boot 3.5.5
- **Language**: Java 17
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA (Hibernate)
- **Security**: Spring Security + JWT
- **Documentation**: SpringDoc OpenAPI 3.0 (Swagger)

### AI/ML
- **Language**: Python 3.11
- **Framework**: Flask
- **Libraries**: NetworkX, Pandas, NumPy
- **Graph Processing**: GraphML for road network analysis

### Infrastructure
- **Cloud**: AWS (ECS, RDS, ECR, ALB)
- **Containerization**: Docker
- **Build Tool**: Gradle

## 🚀 Quick Start

### 1. 환경 설정

```bash
# Repository Clone
git clone https://github.com/your-team/2025_SEASONTHON_TEAM_62_BE.git
cd 2025_SEASONTHON_TEAM_62_BE

# Java 17 설치 확인
java -version

# MySQL 설치 및 실행 (로컬 개발 시)
# Docker 사용 권장:
docker run --name mysql-dev -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=seasonthon -p 3306:3306 -d mysql:8.0
```

### 2. 애플리케이션 설정

```bash
# application.yml 설정 (src/main/resources/)
# application-example.yml을 참고하여 설정

# 빌드 및 실행
./gradlew bootRun
```

### 3. AI 모델 서버 실행

```bash
cd src/main/resources/ai-model

# Python 의존성 설치
pip install flask pandas networkx

# AI 서버 실행
python app.py
```

### 4. API 문서 확인

```
# Swagger UI 접속
http://seasonthon-alb-272154529.ap-northeast-2.elb.amazonaws.com/swagger-ui/index.html
```

## 📁 프로젝트 구조

```
src/
├── main/
│   ├── java/com/kbsw/seasonthon/
│   │   ├── crew/              # 크루 관련 기능
│   │   │   ├── controller/    # 크루 API 컨트롤러
│   │   │   ├── service/       # 크루 비즈니스 로직
│   │   │   ├── domain/        # 크루 엔티티
│   │   │   └── dto/           # 크루 DTO
│   │   ├── favorite/          # 즐겨찾기 관리
│   │   ├── user/              # 사용자 관리
│   │   ├── running/           # 러닝 기록 및 통계
│   │   ├── route/             # 경로 추천
│   │   ├── report/            # 신고 시스템
│   │   ├── recommend/         # AI 추천 시스템
│   │   ├── security/          # 인증/인가
│   │   └── global/            # 공통 설정
│   └── resources/
│       ├── ai-model/          # Python AI 모델
│       │   ├── app.py         # Flask 서버
│       │   ├── path_service.py # 경로 탐색 로직
│       │   ├── visualization.py # 시각화
│       │   └── data/          # 그래프 데이터
│       └── application*.yml   # 환경별 설정
└── test/                      # 테스트 코드
```

## 🗄️ 데이터베이스 스키마

### 주요 테이블

```sql
-- 사용자 테이블
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    email VARCHAR(255),
    nickname VARCHAR(255),
    provider_type ENUM('KAKAO', 'NAVER', 'GOOGLE'),
    provider_id VARCHAR(255),
    role ENUM('USER', 'ADMIN'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 최근 경로 테이블 (신규 개발)
CREATE TABLE recent_paths (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    waypoints TEXT,           -- JSON 형태의 경로 좌표
    saved_polyline TEXT,      -- 폴리라인 데이터
    used_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 크루 테이블
CREATE TABLE crews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    host_id BIGINT NOT NULL,
    start_location VARCHAR(255),
    start_time TIMESTAMP,
    distance_km DECIMAL(5,2),
    duration_min INT,
    max_participants INT,
    safety_level ENUM('LOW', 'MEDIUM', 'HIGH'),
    safety_score INT,
    status ENUM('OPEN', 'CLOSED', 'COMPLETED'),
    FOREIGN KEY (host_id) REFERENCES users(id)
);

-- 즐겨찾기 테이블
CREATE TABLE favorites (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    saved_polyline TEXT,
    safety_level ENUM('LOW', 'MEDIUM', 'HIGH'),
    safety_score INT,
    distance_km DECIMAL(5,2),
    duration_min INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

## 🤖 AI 모델 구조

### 경로 추천 알고리즘
```python
# 주요 기능
- 그래프 기반 경로 탐색 (NetworkX A* 알고리즘)
- 3가지 경로 타입: safe, shortest, balanced
- 안전 점수 기반 가중치 계산
- 순환 경로 생성 (원형 경로)
- 거리 기반 경로 필터링 (±15% 허용 범위)

# 데이터 소스 (달서구 실제 도로 데이터)
- dalseo_real_graph.graphml: 달서구 도로 네트워크 그래프
- nodes_final_with_safety_score.csv: 노드별 안전 점수 (100점 만점)
- floating_pop/*.csv: 시간대별 유동 인구 데이터

# AI 서버 API
- POST /api/routes/recommend: 경로 추천 (Flask 5000포트)
- POST /api/selected-route: 선택된 경로 저장
```

## 🔧 개발 환경 설정

### application.yml 예시
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/seasonthon
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.MySQLDialect
  
  security:
    oauth2:
      client:
        registration:
          kakao:
            client-id: ${KAKAO_CLIENT_ID}
            client-secret: ${KAKAO_CLIENT_SECRET}

jwt:
  secret: ${JWT_SECRET}
  access-token-validity: 3600000
  refresh-token-validity: 604800000

ai:
  model:
    base-url: http://localhost:5000
```

## 🚀 배포 가이드

### AWS 배포
```bash
# ECR 리포지토리 생성
./create-ecr.sh

# RDS 인스턴스 생성
./create-rds.sh

# ECS 클러스터 및 서비스 생성
./create-ecs.sh

# 업데이트 배포
./deploy-update.sh
```

자세한 배포 가이드는 [AWS-DEPLOYMENT-GUIDE.md](./AWS-DEPLOYMENT-GUIDE.md)를 참조하세요.

## 🧪 테스트

### 단위 테스트 실행
```bash
./gradlew test
```

### API 테스트 (Recent Paths 예시)
```bash
# POST 요청 테스트
curl -X POST http://localhost:8080/api/recent-paths/complete \
  -H "Content-Type: application/json" \
  -d '{
    "waypoints": [[35.123456, 129.123456], [35.123457, 129.123457]],
    "savedPolyline": "test_polyline"
  }'

# GET 요청 테스트
curl -X GET http://localhost:8080/api/recent-paths
```

## 🔍 최근 개발 내용

### ✅ Recent Paths API 구현 완료
- **경로 완주 저장 API**: 사용자가 경로를 완주하면 자동으로 DB에 저장
- **최근 경로 조회 API**: 사용자별 최근 사용한 경로 목록 제공
- **MySQL 호환성**: JSON 데이터 타입을 TEXT로 처리하여 안정성 확보
- **인증 제거**: 개발 단계에서 테스트 용이성을 위해 인증 없이 접근 가능

### 🔧 기술적 개선사항
- JPA 엔티티 최적화 및 MySQL 호환성 개선
- CORS 설정 개선으로 프론트엔드 통합 준비
- Swagger UI 완전 지원으로 API 테스트 환경 구축

## 👥 팀 정보

**2025 SEASONTHON TEAM 62**
- Backend Development Team
- Frontend Development Team  
- AI/ML Development Team
- DevOps Team

---

## 📞 지원 및 문의

프로젝트 관련 문의사항이나 버그 리포트는 [Issues](https://github.com/your-team/2025_SEASONTHON_TEAM_62_BE/issues)를 통해 남겨주세요.

**Happy Running! 🏃‍♀️🏃‍♂️**
