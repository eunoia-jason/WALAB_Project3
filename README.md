<img width="512" alt="image" src="https://github.com/eunoia-jason/WALAB_Project3/assets/62330979/9b0dc895-4568-40f1-8cc2-779acc13ea96"># WALAB_Project3

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

### DB Table
- USER: 유저 정보를 저장하는 Table입니다.
<img width="590" alt="image" src="https://github.com/eunoia-jason/WALAB_Project3/assets/62330979/dec98eb7-cba5-4b25-8350-525027348614">

- LECTURE: 강의 정보를 저장하는 Table입니다.
<img width="574" alt="image" src="https://github.com/eunoia-jason/WALAB_Project3/assets/62330979/e6d5936e-acd9-4900-96c4-5faf5431133b">

- ENROLLMENT: 유저가 여러 강의를 수강 신청하였음을 저장하고 있는 다대다 관계 Table입니다.
<img width="512" alt="image" src="https://github.com/eunoia-jason/WALAB_Project3/assets/62330979/8dbbcedc-d6a8-416f-a49b-7317975ed94f">

## 스크린샷
<img width="894" alt="image" src="https://github.com/eunoia-jason/WALAB_Project3/assets/62330979/80a94c1d-f1b3-4148-82d2-f491b2028f74">
<img width="375" alt="image" src="https://github.com/eunoia-jason/WALAB_Project3/assets/62330979/07710fe8-8e6f-486c-ad97-1cc5c66b2750">
<img width="716" alt="image" src="https://github.com/eunoia-jason/WALAB_Project3/assets/62330979/3b7b9678-7c85-4bc9-b76d-2b5169087d60">
<img width="712" alt="image" src="https://github.com/eunoia-jason/WALAB_Project3/assets/62330979/0be27150-720e-40e9-b2ff-6e944cebf17c">
<img width="713" alt="image" src="https://github.com/eunoia-jason/WALAB_Project3/assets/62330979/a28fe227-9f14-49cb-9064-641523b1481f">
<img width="583" alt="image" src="https://github.com/eunoia-jason/WALAB_Project3/assets/62330979/6a697f4a-9b09-4f8f-89c2-de537e443e37">
<img width="585" alt="image" src="https://github.com/eunoia-jason/WALAB_Project3/assets/62330979/a29c1e5a-6cd0-493c-ae1f-74e334b3f3b0">
<img width="586" alt="image" src="https://github.com/eunoia-jason/WALAB_Project3/assets/62330979/1675144a-44bf-47d1-b4d1-0d733780b3aa">
<img width="586" alt="image" src="https://github.com/eunoia-jason/WALAB_Project3/assets/62330979/2226ece3-1fe4-4bf8-b64d-eff55e15dccc">
<img width="584" alt="image" src="https://github.com/eunoia-jason/WALAB_Project3/assets/62330979/41851c93-54da-465f-9d68-4ac57083a68e">
<img width="716" alt="image" src="https://github.com/eunoia-jason/WALAB_Project3/assets/62330979/4169b97c-2d06-4efd-bb4a-ecda9ba5ac49">
