# HackSeoul 해커톤

![HackSeoul](</hackseoul_notionbanner.png>)
https://angelhack.com/hackglobal/seoul/

### 선정 주제

> Digital Empowerment - **Coupang’s Challenge Statement**
>> **What we are looking for:** We encourage solutions that can seamlessly integrate with or enhance Coupang's existing services to create an even more comprehensive and engaging e-commerce ecosystem.

### 팀원
- Frontend : [iamgyu](https://github.com/iamgyu), [zaiisao](https://github.com/zaiisao)
- Backend : [sunni546](https://github.com/sunni546)

# White Elephant

이 웹 서비스는 [White Elephant Gift Exchage](https://en.wikipedia.org/wiki/White_elephant_gift_exchange) 게임의 아이디어를 바탕으로 한 선물 교환 플랫폼입니다. 사용자는 이 플랫폼에서 직접 팀을 생성하거나 기존 팀에 가입할 수 있으며, 팀이 완성되면 시스템이 자동으로 각 팀원에게 선물을 줄 사람을 무작위로 매칭해줍니다. 이 서비스는 사용자들에게 재미있고 흥미로운 선물 교환 경험을 제공하며, 쿠팡의 기존 e-commerce 생태계와 통합하여 사용자 참여와 만족도를 높이는 데 기여할 수 있습니다.

## 기술 스택

### Front
- HTML/CSS
- JavaScript
- React

### Back
- Java 17
- Spring Boot 3.3
- MySQL
- REST API

## 주요 기능

- 회원 가입 & 로그인
- 팀 생성
    - 비밀번호 설정
    - 선물의 최소 / 최대 가격 지정
- 팀 가입
    - 비밀번호 입력 후, 팀 상세 페이지로 이동
- 역할별 팀 상세 정보 조회
    - 팀원
        - 팀 멤버 조회 및 정보 확인
    - 팀장
        - 팀 완성 및 삭제
            - 팀 완성 시, 자동으로 각 팀원에게 선물을 줄 사람을 무작위로 매칭
        - 팀원 삭제
- 팀 내 팀원 매칭 완료 후, 사용자는 각자 제공된 쿠팡 링크로 이동

## Database

### ERD

![White-Elephant](</whiteElephant-back/White-Elephant.png>)

## API (Endpoints)

### users

| Method | Url              | Decription |
| ------ | ---------------- | ---------- |
| GET    | /users/{userId}  | 특정 '사용자' 조회 |
| POST   | /users/login     | '사용자' 회원가입(추가) |
| POST   | /users/join      | '사용자' 로그인 |

### teams

| Method | Url                                                  | Decription |
| ------ | ---------------------------------------------------- | ---------- |
| GET    | /users/{userId}/teams                                | ACTIVE 상태의 전체 '팀' 목록 조회 |
| GET    | /users/{userId}/teams/me                             | 특정 '사용자'가 속한 전체 '팀' 목록 조회 |
| POST   | /users/{userId}/teams                                | '팀' 생성 및 LEADER 역할의 '멤버' 생성 |
| POST   | /users/{userId}/teams/join                           | '팀' 가입 및 MEMBER 역할의 '멤버' 생성 |
| GET    | /users/{userId}/teams/{teamId}                       | 특정 '팀'의 정보 조회 |
| GET    | /users/{userId}/teams/{teamId}/details               | 특정 '팀'의 정보 및 '멤버' 목록 조회 |
| GET    | /users/{userId}/teams/{teamId}/receiver              | COMPLETED 상태의 '팀'에서 매칭된 '멤버' 조회 |
| PATCH  | /users/{userId}/teams/{teamId}                       | 특정 '팀'의 상태를 COMPLETED로 변경 및 '멤버' 매칭 |
| DELETE | /users/{userId}/teams/{teamId}                       | 특정 '팀' 삭제 |
| DELETE | /users/{userId}/teams/{teamId}/members/{memberId}    | 특정 '팀'에 속한 특정 '멤버' 삭제 |

## UI

### 회원가입 페이지

![join](<whiteElephant-front/UI/join.png>)

### 로그인 페이지

![login](<whiteElephant-front/UI/login.png>)

### 메인 페이지

![main](<whiteElephant-front/UI/main.png>)

- 버튼을 통해 마이페이지, 팀 생성 페이지로 이동
- 로그아웃 버튼 클릭 시 로그아웃되며 로그인 페이지로 이동
- 목록에 있는 팀 클릭 시 팀에 속해있지 않으면 비밀번호를 입력하는 창이 뜨고, 팀에 속해 있으면 detail 페이지로 이동
- 팀 목록은 화면에 최대 6개 까지 뜨며 6개를 넘어갈 경우 아래 숫자 버튼을 통해 확인 가능

### 팀 생성 페이지

![createteam](<whiteElephant-front/UI/createteam.png>)

### 팀 상세 페이지

![detail](<whiteElephant-front/UI/detail.png>)

- 로고를 통해 메인 페이지로 이동
- 팀 이름과 선물 구매의 최소~최대 금액, 속해 있는 멤버들의 이름을 보여줌
- 해당 팀을 만든 리더인 경우 팀 삭제하기, 팀 완성하기 버튼을 사용할 수 있음
- 리더가 아닌 경우 팀 삭제하기, 팀 완성하기 버튼 클릭 시 알림 창을 통해 에러 메시지 표시
- 멤버가 많아질 수록 아래의 버튼을 클릭해 화면을 넘겨 멤버 확인 가능

### 유저 개인 페이지

![mypage](<whiteElephant-front/UI/mypage.png>)

- 유저 이미지 및 이름을 확인 가능
- 내가 만든 팀, 멤버로 속한 팀, 모집이 완료 된 팀을 각각 모음으로 볼 수 확인 가능
- 내가 만든 팀, 멤버로 속한 팀을 클릭 시 해당 팀의 디테일 페이지로 넘어감
- 모집 완료 된 팀을 클릭 시 모달 페이지를 띄워줌

### 멤버 매칭 결과 모달 페이지

![modal](<whiteElephant-front/UI/modal.png>)

- 내가 선물을 보내야 하는 사람을 알려줌
- 쿠팡 이동 버튼을 통해 쿠팡 사이트로 이동
- 창 끄기 버튼으로 모달창을 닫을 수 있음
