# WALAB_Project3

## 프로젝트 개요
- WALAB_Project3는 CRUD(Create, Read, Update, Delete) 기능과 sqlite3 DB를 통해 데이터를 관리하는 Java 프로젝트입니다.
- 이 프로젝트는 기존의 Project1을 확장하여 sqlite3 DB 기반 데이터 관리 기능을 추가하였습니다. 또한 Project1의 논리적, 구조적 결함을 개선하였습니다.
- 인프런 홈페이지의 강의, 유저 데이터 관리를 콘솔 프로그램으로 제작하였습니다. https://www.inflearn.com/

## 사용된 클래스 & 라이브러리

### 클래스
- main
    - Main: 프로그램의 시작점, 로그인 전, 관리자 모드, 일반 유저 모드의 흐름을 제어합니다.
- controllers
    - UserController: 사용자 정보(수강 정보)를 관리합니다.
    - LectureController: 강의 정보를 관리합니다.
- views
    - UserView: 사용자 정보를 출력합니다.
    - LectureView: 강의 정보를 출력합니다.
- database
    - SQLiteManager: DB의 기본 정보, 세팅과 연결을 담당합니다.
    - DDLService: DB의 Table 구조를 정의합니다.
    - DMLService: Table의 C, U, D를 담당합니다.
    - DQLService: Table의 R을 담당합니다.
  
### 라이브러리
- lombok: Java 클래스의 보일러플레이트 코드를 줄이기 위해 사용합니다.(getter, setter, constructor)
- sqlite-jdbc: Java에서 SQLite 데이터베이스와 상호 작용하기 위한 JDBC 드라이버입니다.
- slf4j-simple: Simple Logging Facade for Java (SLF4J) 프레임워크에서 제공하는 간단한 로깅 구현체입니다.

### 데이터 저장 포맷
- JSON (JavaScript Object Notation) 포맷을 사용하여 사용자 데이터와 강의 정보를 파일에 저장하고 관리합니다.

- readData(lectureData.json, userData.json) 미리 저장하여 읽기 위한 강의, 유저 데이터입니다.
<img width="467" alt="image" src="https://github.com/eunoia-jason/WALAB_Project2/assets/62330979/4b68d9af-de73-4eac-b432-7729d7a7d287">

- writeData(lectureData.json, userData.json) -> 회원가입, 회원 탈퇴, 정보 수정 등 / CUD 발생 시 파일로 저장합니다.
<img width="824" alt="image" src="https://github.com/eunoia-jason/WALAB_Project2/assets/62330979/dc23a9e5-8e23-4218-8a63-4e140fdc015b">

## 스크린샷
