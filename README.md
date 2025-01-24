# Hot Deal Wave <img width="100" alt="image" src="https://github.com/user-attachments/assets/44911a59-611f-465a-a7e0-8d0f301bcbe8" align=left >

대규모 트래픽을 견딜 수 있는 안정성을 갖춘 핫딜 이커머스 플랫폼으로,
선착순 쿠폰 발급과 타임 세일 기능을 제공하는 서비스

<br/>

## ⛳️ 프로젝트 목표

> <i>기간: 2024.12.23 ~ 2025.01.27</i>

- 대규모 트래픽 처리를 위한 주문 생성 성능 개선
- Kafka 또는 Redis를 사용한 동시성 처리로 쿠폰 순차적 발급
- 데이터 일관성 유지를 통한 정확한 재고 수량 관리
- AWS, Docker, Github Actions을 이용한 배포를 통해 개발 효율성 증대

<br/>

## ⚒️ 기술 스택

**Backend**

<p>
<img src="https://img.shields.io/badge/JDK 17-E38836?style=flat-square&logo=openJdk&logoColor=white">
<img src="https://img.shields.io/badge/Spring Boot 3.4.2-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>
<img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white"/>
<br />
<img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=flat-square&logo=spring&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white"/>
<br />
<img src="https://img.shields.io/badge/Spring Cloud-6DB33F?style=flat-square&logo=spring&logoColor=white"/>
<img src="https://img.shields.io/badge/Eureka-6DB33F?style=flat-square&logo=spring&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Gateway-6DB33F?style=flat-square&logo=spring&logoColor=white"/>
<br />
<img src="https://img.shields.io/badge/Kafka-231F20?style=flat-square&logo=apachekafka&logoColor=white"/>
<img src="https://img.shields.io/badge/ElasticSearch-005571?style=flat-square&logo=elasticsearch&logoColor=white"/>

</p>

**Database**

<p>
<img src="https://img.shields.io/badge/MySQL 8-08668E?style=flat-square&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/Redis-FF0000?style=flat-square&logo=redis&logoColor=white">
</p>

**Test**

<p>
<img src="https://img.shields.io/badge/JUnit 5-25A162?style=flat-square&logo=junit5&logoColor=white"/>
<img src="https://img.shields.io/badge/JMeter-D22128?style=flat-square&logo=apachejmeter&logoColor=white"/>
<p />

**Monitoring**

<p>
<img src="https://img.shields.io/badge/Prometheus-E6522C?style=flat-square&logo=prometheus&logoColor=white"/>
<img src="https://img.shields.io/badge/Grafana-F46800?style=flat-square&logo=grafana&logoColor=white"/>
<img src="https://img.shields.io/badge/Grafana%20Loki-FF9900?style=flat-square&logo=grafana&logoColor=white"/>
</p>

**Docs**

<p>
<img src="https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=swagger&logoColor=black"/>
</p>

**Infra**

<p>
<img src="https://img.shields.io/badge/AWS EC2-FF9900?style=flat-square&logo=amazonec2&logoColor=white"/>
<img src="https://img.shields.io/badge/AWS RDS-527FFF?style=flat-square&logo=amazonrds&logoColor=white"/>
<img src="https://img.shields.io/badge/AWS%20ECR-FF9900?style=flat-square&logo=amazonaws&logoColor=white"/>
<img src="https://img.shields.io/badge/AWS%20S3-44B8A1?style=flat-square&logo=amazonaws&logoColor=white"/>
<br />
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white"/>
<img src="https://img.shields.io/badge/Docker Compose-2496ED?style=flat-square&logo=docker&logoColor=white"/>
</p>

**CI/CD**

<p>
<img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=flat-square&logo=githubactions&logoColor=white"/>
</p>

<br/>

## 🧬 아키텍처 설계도

<img width="1518" alt="image" src="https://github.com/user-attachments/assets/c252a577-1080-48fa-8f38-55c33699ac81" />

<br/><br/>

## 🌏 서비스 구성

### 👥 User Service

- 회원가입 및 로그인
- 사용자 정보 관리
- 배송지 등록 및 관리
- 이메일 인증

### 🏢 Company Service

- 업체 등록 및 관리

### 🎁 Produt Service

- 상품 등록 및 관리
- 프로모션 등록 및 관리
- 리뷰 등록 및 관리

### 💳 Coupon Service

- 쿠폰 등록 및 관리
- 선착순 쿠폰 발급

### 💰 Order Service

- 주문 등록 및 관리

### 📱 Payment Service

- 결제 등록 및 관리
- 카카오페이 연동

<br/>

## ⚙️ 기술적 의사 결정

#### [🚀 선착순 쿠폰 발급 Optimistic Lock vs Redisson](https://teamsparta.notion.site/Optimistic-Lock-vs-Redisson-3eea45de21e5447fa210503a55e6f1db) <br />

#### [🚀 주문 생성 성능 개선 비동기 처리 or DB Pool or 캐싱](https://teamsparta.notion.site/12-9700-5c53365016c1416993c6876044a08d6c?p=27647d3f807d447ab5f5a1f386f628ba&pm=s) <br />

#### [🚀 재고 차감 및 복구 동시성 처리 Optimistic Lock vs Kafka](https://teamsparta.notion.site/12-9700-5c53365016c1416993c6876044a08d6c?p=f5e09965e23e4948a8b127fb485539ad&pm=s) <br />

<br/>

## 🚨 [트러블 슈팅](https://www.notion.so/teamsparta/1792dc3ef51481d08c52c339164bf06e?v=1792dc3ef5148163bcd2000c13520b27)

#### [🕊️ 선착순 쿠폰 발급 성능 개선 ](https://teamsparta.notion.site/12-9700-5c53365016c1416993c6876044a08d6c?p=54c49b4f4ed14fadaecd39dd9779b04e&pm=s) <br />

#### [🕊️ 주문 생성 성능 개선 ](https://teamsparta.notion.site/12-9700-5c53365016c1416993c6876044a08d6c?p=14e5f67a1b494743b73855046b57d44c&pm=s) <br />

#### [🕊️ 업체 조회 성능 개선 ](https://teamsparta.notion.site/12-9700-5c53365016c1416993c6876044a08d6c?p=ac72882aac304d83a79311d0954e25e6&pm=s) <br />

#### [🕊️ 로그 모니터링 시스템 구축 ](https://teamsparta.notion.site/12-9700-5c53365016c1416993c6876044a08d6c?p=c66f731a92bd4ba18cf9974562ad0df2&pm=s) <br />

#### [🕊️ 상품 재고 차감 동시성 제어 ](https://teamsparta.notion.site/12-9700-5c53365016c1416993c6876044a08d6c?p=aa93a88316e545d3a191aa0b5a608700&pm=s) <br />

#### [🕊️ 상품 목록 조회에 대한 성능 개선 ](https://teamsparta.notion.site/12-9700-5c53365016c1416993c6876044a08d6c?p=1d52a8ad400e47b29520f1c703317493&pm=s) <br />

<br/><br/>

## 📝 ERD

<img width="1518" alt="image" src="https://github.com/user-attachments/assets/f27e5183-6f2f-4036-b627-85b98d0dde6c" />

<br/>
<br/>

### 🧑🏻‍💻 팀 멤버 및 역할

| <img src="https://ca.slack-edge.com/T06B9PCLY1E-U07STL0M0NQ-1cd2ad3cca6b-512" width="100px;" alt=""/><br /><sub><b>[신민철](https://github.com/MCshin00)</b></sub></a><br /> | <img src="https://avatars.githubusercontent.com/u/114653979?v=4" width="100px;" alt=""/><br /><sub><b>[김지수](https://github.com/strongcookdas)</b></sub></a><br /> | <img src="https://avatars.githubusercontent.com/u/69751205?v=4" width="100px;" alt=""/><br /><sub><b>[박송이](https://github.com/sooooooongyi)</b></sub></a><br /> | <img src="https://github.com/user-attachments/assets/54a77ab1-9828-44e6-8d3b-cffb6828d2f2" width="100px;" alt=""/><br /><sub><b>[박강현](https://github.com/pkhyun)</b></sub></a><br /> | <img src="https://avatars.githubusercontent.com/u/128135999?v=4" width="100px;" alt=""/><br /><sub><b>[김예원](https://github.com/yewonkim301)</b></sub></a><br /> |
|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------------------------------------------:|
|                                            💡 Eureka Server, Gateway <br> 💡 User 도메인 <br> 💡 인프라 구축 및 배포 <br> 💡 모니터링 시스템 구축                                             |                                                                 💡 Order 도메인 <br> 💡 Payment 도메인                                                                  |                                                     💡 Company 도메인 <br> 💡 Review 도메인 <br> 💡 File 도메인 <br>                                                     |                                                                        💡 Coupon 도메인 <br> 💡 Coupon-Info 도메인                                                                         |                                                      💡 Product 도메인 <br> 💡 Promotion 도메인 <br> 💡 File 도메인                                                      |

<br/>
