# hackSeoul-whiteElephant

## 기능

- 팀 생성
- 팀 가입
- 팀원
    - 팀 페이지에서 멤버 조회 및 정보 확인
- 팀장
    - 팀 완성
    - 팀 삭제
    - 팀원 삭제
- 내가 선물 줄 사람 확인
- 회원 가입
- 로그인

## 페이지

- 회원 가입 페이지
- 로그인 페이지
- 팀 리스트 페이지
    - 팀 가입
    - 팀 생성
- 팀 페이지
    - (before) 비번 인증 페이지
    - 팀 정보, 멤버 리스트
- 개인 페이지
    - 개인 정보
    - 팀 리스트
        - 팀 페이지 진입 가능
        - 팀장인 경우 팀 삭제, 팀원 삭제
        - 선물 주는 상대 사용자 정보
            - 선물하기 : 쿠팡 연결

## API (Endpoints)


## db

- presents
    - id PK
    - receiver
    - 주문한 사람 user_id FK
- users
    - id PK
    - email
    - password
    - name
    - phone
- teams
    - id PK
    - name
    - price
    - password
    - is_completed [FALSE, TRUE]
- members
    - id PK
    - Role [LEADER, MEMBER]
    - team_id FK
    - user_id FK