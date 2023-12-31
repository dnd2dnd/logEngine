﻿# logEngine

## 소개

시스템에 대한 로그를 지속적으로 기록하고, 이러한 로그 데이터에서 특정 문자열을 선택하여 필요한 정보를 신속하게 감지하여 사용자에게 알림을 전송할 수 있다.

이를 통해 시스템 상태나 특정 이벤트에 대한 실시간 모니터링 및 로그 확인이 가능하다.


![image](https://github.com/dnd2dnd/logEngine/assets/68494227/8368f32e-0397-4076-bf5b-c5d69fb9ca9a)

<br>

## 기능

- 로그 수집
- 알림 기능

<br>

## 성과 및 배운점

- flunet-bit를 사용하여 로그 수집을 할 수 있게 되었습니다.
- 캐시를 적절히 활용하여 동일한 데이터 반복 조회를 최소화하고 시스템의 불필요한 작업을 최적화하여 응답시간의 효율성을 높였습니다.
- Kafka를 사용하여 실시간 이벤트 처리에 대한 이해를 키웠습니다.
- Docker-Compose를 활용하여 환경 구축을 손쉽게 할 수 있도록 하였습니다.
- 테스트 코드 작성을 통해 코드 품질을 높이고, 객체 지향 프로그래밍의 원칙을 준수하여 개발함으로써 재사용성을 향상시켰습니다.

<br>

## 배포

```yaml
version: '3'
  timescaledb:
    container_name: timescale
    image: timescale/timescaledb-ha:pg14-latest
    user: "root"
    environment:
      POSTGRES_USER: { user_name }
      POSTGRES_DB: { database_name }
      POSTGRES_PASSWORD: { user_password }
      PGDATA: "/var/lib/postgres/data"
      TZ: "Asia/Seoul" # 한국 시간대 설정
    ports:
      - "5432:5432"
    volumes:
      - ./timescaledb/data:/var/lib/postgres/data  
```

- docker-compose.yml-example의 database 환경 부분 수정

<br>

```bash
$ docker-compose up -d --build
```
