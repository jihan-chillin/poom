# 지역기반 커뮤니티 POOM :two_women_holding_hands:

## 목차
 1. [개발목적 및 목표](#목적)
 2. [팀원소개](#팀원소개)
 3. [개발 환경](#개발환경)
 4. [DB 구조](#DB구조)
 5. [기능](#기능)
 6. [느낀점 및 배운 것들](#느낀점)
 7. [POOM 기획과정](#POOM)
----
## 개발목적 및 목표

### 목적
 - KH 정보교육원 Rclass 파이널 프로젝트
 - 지역민들의 정보공유와 친목도모를 위해 만들었다.

### 목표
 - SpringBoot를 활용해 프레임워크를 효율적으로 이용해보자.  
 - ajax를 통해 사용자에게 최대한 화면의 깜빡임을 줄 일 수 있도록 만들자
------

## 팀원소개
- 장희웅(팀장)
- 김지한
- 이하은
- 여진경
- 홍서연
- 김세희
------

## 개발환경

### 운영체제
- Mac os 11.4
- Windwos 10

### 개발도구
- IntelliJ 21.01
- STS3
- Visual Studio Code

### 사용언어
**FE(Front-End)**
- Html
- css
- javascript
- jQuery
- thymeleaf

**BE(Back-End)**
 - Java 11 / Ajax
 - Was : Apache Tomcat 8.5
 - Database
 - Maven
 - Oracle 11g
 - MyBatis

**FrameWork**
- Spring Boot 2.5.2 ( MVC 패턴 적용 ) 

**사용한 오픈 API** 
 - 네이버 geolocation 
 - 메일인증 API
 - Ckeditor 스마트에디터
 - KakaoPay 결제 API

---
## DB구조
![스크린샷 2021-08-10 오전 12 52 01](https://user-images.githubusercontent.com/44770275/128790985-f04d2135-3f2b-4f24-ab9b-61e08acd0d4b.png)


[https://www.erdcloud.com/d/FfWSdWRWFQ96i7EHw](https://www.erdcloud.com/d/FfWSdWRWFQ96i7EHw)

회원 테이블을 중심으로 데이터 베이스를 설계했습니다.

---

![스크린샷 2021-08-10 오전 1 02 30](https://user-images.githubusercontent.com/44770275/128790969-f884513e-35a2-4e60-b074-e0c4934ff510.png)
![스크린샷 2021-08-10 오전 1 02 43](https://user-images.githubusercontent.com/44770275/128790972-61814671-9445-4b9e-b0f2-dc955fda1e49.png)
![스크린샷 2021-08-10 오전 1 02 55](https://user-images.githubusercontent.com/44770275/128790974-f00406d6-8b01-4c7e-bd46-f8c61474b31e.png)
![스크린샷 2021-08-10 오전 1 03 00](https://user-images.githubusercontent.com/44770275/128790975-da5f4639-2e32-4d8e-9611-104a988a3039.png)
![스크린샷 2021-08-10 오전 1 03 06](https://user-images.githubusercontent.com/44770275/128790977-b3f30bc6-27d6-49f1-95e3-ae9cacf52c6a.png)
![스크린샷 2021-08-10 오전 1 03 11](https://user-images.githubusercontent.com/44770275/128790978-c96f6fc4-fce5-4d1f-add9-b719327271d9.png)
![스크린샷 2021-08-10 오전 1 03 18](https://user-images.githubusercontent.com/44770275/128790979-fc2e6d26-fe55-4bf2-a8cb-d3379f286511.png)
![스크린샷 2021-08-10 오전 1 03 25](https://user-images.githubusercontent.com/44770275/128790980-cec371e2-ce97-424d-a00f-fd5ced5a43ad.png)
![스크린샷 2021-08-10 오전 1 03 30](https://user-images.githubusercontent.com/44770275/128790981-b62d3a76-b797-4245-9955-e2b33ea91aa2.png)
![스크린샷 2021-08-10 오전 1 03 33](https://user-images.githubusercontent.com/44770275/128790982-9145d4b8-d522-4dc5-a59e-0ace75fb230f.png)
![스크린샷 2021-08-10 오전 1 03 41](https://user-images.githubusercontent.com/44770275/128790983-278de9a8-ca37-4de4-8dde-bca9497dbc57.png)
![스크린샷 2021-08-10 오전 1 03 47](https://user-images.githubusercontent.com/44770275/128790984-2ba0d84b-dd32-41f7-8a0c-ecd8195e69c2.png)

- 기능별로 디렉토리를 분리해 프로젝트를 관리했습니다.

---

## 기능
**1. 회원가입 / 로그인**
 - 회원가입시 지역인증을 받아 회원의 지역을 인증받도록 했습니다.
 - 메일 인증을 통해 본인인증을 하도록 했습니다.
 - 빈칸 / null 검증을 했습니다.

**2. 게시글**
 - 회원은 본인이 인증받은 지역의 게시글만 볼 수 있습니다.
 - 다른 지역을 보기 위해서는 이용권을 구매해야합니다.
 - 게시글 작성시 해시태그를 입력 할 수 있고 동일한 해시태그가 달린 게시물을 모아서 볼 수 있습니다.
 - 게시글에 댓글, 답글을 작성 할 수 있습니다.
 - ckeditor API를 사용하여 사용자의 편의성을 높였습니다.
 - 지역별 게시글을 모아서 볼 수 있습니다.
 - 게시글은 좋아요를 눌러서 찜할 수 있습니다.
 - 찜한 게시글은 마이페이지에서 조회가능합니다.
 - 게시글은 수정,삭제가 가능합니다.

 **3. 실시간 알림**
  - 회원이 작성한 게시글에 좋아요가 눌렸을 때와 댓글이 달렸을 때 그리고 회원에게 쪽지가 왔을때 시 실시간으로 알림이 생성됩니다.
  - 알림은 읽음 처리를 할 수 있고 보고 싶지않은 알림은 삭제처리를 할 수 있습니다.
  - 알림은 최근 10개만을 볼 수 있습니다. 

**4. 실시간 채팅**
 - 채팅방을 생성할 수 있습니다. 인원 수를 제한 할 수있습니다. 
- 실시간으로 채팅이 가능합니다.
- 채팅내용은 최근 일주일까지 볼 수 있습니다.
- 마이페이지에서 참여하고 있는 채팅방과 관심채팅방을 볼 수 있습니다.

**5. 해시태그**
 - 게시글 작성시 해시태그를 만들 수 있습니다.
 - 관심있는 해시태그가 있다면 마이페이지에서 등록가능합니다.
 - 마이페이지에서 관심 해시태그별로 게시글을 모아서 볼 수 있습니다.

**6. 쪽지**
 - 회원은 다른 회원에게 쪽지를 발송, 수신할 수 있습니다.
 - 다른 회원이 읽지 않았다면 발송취소를 할 수 있습니다.
 - 쪽지는 휴지통으로 이동한 후 완전 삭제가 가능합니다.

**7.결제**
- 회원은 다른 지역 게시글을 보기위해 결제가 필요합니다.
- 결제는 Kakaopay API를 사용하여 진행됩니다.

**8.신고**
- 댓글,게시글,채팅방은 회원이 신고가 가능합니다.
- 일정 신고 수가 누적이 되면 자동 숨김처리 되고 숨김처리된 것은 관리자가 숨김해제 또는 삭제할 수 있습니다.
- 회원이 작성한 댓글, 게시글, 채팅방이 일정 수 이상 삭제가 된다면 회원은 자동 정지처리됩니다.

**9.관리자페이지**
- 관리자는 게시판별 공지사항을 작성, 삭제, 수정이 가능합니다.
- 최근 매출 내역을 조회할 수 있습니다.
- 숨김처리된 글을 삭제하거나 복구할 수 있습니다.

**10.검색**
- 검색창을 통해 게시글을 검색 할 수 있습니다.
---
## 느낀점 및 배운 것들
1. 프레임워크를 사용 할 시 빠르고 효율적으로 개발이 가능하다는 것을 느꼈다.
2. JQuery를 통한 ajax 방식을 사용하여 화면의 부분만을 바꾸는 방법을 사용했는데 React가 나온 이유를 알 것 같았다.
3. 이미지를 폴더에 저장하고 그것들을 화면에 띄우는 과정에서 서버 상의 경로와 로컬 상의 경로 차이를 알 수 있었다.
4. 첫 프로젝트와는 다르게 코드를 재활용하는 파트를 따로 만들어놓는 등 효율에 대해 생각하면서 임한 프로젝트라 뜻깊었다. 
---
## POOM 기획과정
[POOM 노션] https://www.notion.so/1bf6d666e9094815a21ce046a1880162

- 필독사항
- 회의록
- 와이어프레임
- 사이트 기능
- 게시판 분류기준
