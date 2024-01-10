package org.example.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DMLService {
    final String INSERT_SQL = "INSERT INTO USER ( NAME, EMAIL, PASSWORD, RECENT_LOGIN_DATE, REG_DATE) VALUES ( ?,?,?,?,? )";
    final String INSERT_LECTURE = "INSERT INTO LECTURE ( TITLE, LECTURER, CATEGORY, COUNT, REG_DATE) VALUES ( ?,?,?,0,? )";
    final String INSERT_ENROLLMENT = "INSERT INTO ENROLLMENT ( USER_ID, LECTURE_ID) VALUES ( ?,? )";
    final String UPDATE_SQL = "UPDATE USER SET NAME = ?, EMAIL = ?, PASSWORD = ?, RECENT_LOGIN_DATE = ? WHERE ID = ?";
    final String UPDATE_LECTURE = "UPDATE LECTURE SET TITLE = ?, LECTURER = ?, CATEGORY = ?, COUNT = ? WHERE LECTURE_ID = ?";
    final String DELETE_SQL = "DELETE FROM USER WHERE ID = ?";
    final String DELETE_LECTURE = "DELETE FROM LECTURE WHERE LECTURE_ID = ?";
    final String DELETE_ENROLLMENT = "DELETE FROM ENROLLMENT WHERE USER_ID = ? AND LECTURE_ID = ?";

    Connection conn;
    PreparedStatement pstmt;

    public DMLService(Connection conn) {
        this.conn = conn;
    }

    // 데이터 삽입 함수
    public int insertPerson(HashMap<String, Object> dataMap) throws SQLException {

        int inserted = 0;

        try {
            // PreparedStatement 생성
            pstmt = conn.prepareStatement(INSERT_SQL);

            // 입력 데이터 매핑
            pstmt.setObject(1, dataMap.get("NAME"));
            pstmt.setObject(2, dataMap.get("EMAIL"));
            pstmt.setObject(3, dataMap.get("PASSWORD"));
            pstmt.setObject(4, dataMap.get("RECENT_LOGIN_DATE"));
            pstmt.setObject(5, dataMap.get("REG_DATE"));

            // 쿼리 실행
            pstmt.executeUpdate();

            // 입력건수  조회
            inserted = pstmt.getUpdateCount();

            // 트랜잭션 COMMIT
            conn.commit();
            System.out.println("회원 가입 완료.");
            System.out.println("====================\n");
        } catch (SQLException e) {
            // 오류출력
            System.out.println(e.getMessage() + "\n");
            // 오류
            inserted = -1;
            // 트랜잭션 ROLLBACK
            if( conn != null ) {
                conn.rollback();
            }
        } finally {
            // PreparedStatement 종료
            if( pstmt != null ) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // 결과 반환  - 입력된 데이터 건수
        return inserted;
    }

    // 데이터 삽입 함수
    public int insertLecture(HashMap<String, Object> dataMap) throws SQLException {

        int inserted = 0;

        try {
            // PreparedStatement 생성
            pstmt = conn.prepareStatement(INSERT_LECTURE);

            // 입력 데이터 매핑
            pstmt.setObject(1, dataMap.get("TITLE"));
            pstmt.setObject(2, dataMap.get("LECTURER"));
            pstmt.setObject(3, dataMap.get("CATEGORY"));
            pstmt.setObject(4, dataMap.get("REG_DATE"));

            // 쿼리 실행
            pstmt.executeUpdate();

            // 입력건수  조회
            inserted = pstmt.getUpdateCount();

            // 트랜잭션 COMMIT
            conn.commit();
            System.out.println("강의 등록 완료.");
            System.out.println("====================\n");
        } catch (SQLException e) {
            // 오류출력
            System.out.println(e.getMessage() + "\n");
            // 오류
            inserted = -1;
            // 트랜잭션 ROLLBACK
            if( conn != null ) {
                conn.rollback();
            }
        } finally {
            // PreparedStatement 종료
            if( pstmt != null ) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // 결과 반환  - 입력된 데이터 건수
        return inserted;
    }

    // 데이터 삽입 함수
    public int insertEnrollment(int id, int input, Map<String, Object> updateMap, boolean update) throws SQLException {

        int inserted = 0;

        try {
            // PreparedStatement 생성
            pstmt = conn.prepareStatement(INSERT_ENROLLMENT);

            // 입력 데이터 매핑
            pstmt.setObject(1, id);
            pstmt.setObject(2, input);

            // 쿼리 실행
            pstmt.executeUpdate();

            // 입력건수  조회
            inserted = pstmt.getUpdateCount();

            // 트랜잭션 COMMIT
            conn.commit();
            updateLecture(updateMap, update);
            System.out.println("수강 신청 완료.");
            System.out.println("====================\n");
        } catch (SQLException e) {
            // 오류출력
            System.out.println(e.getMessage() + "\n");
            // 오류
            inserted = -1;
            // 트랜잭션 ROLLBACK
            if( conn != null ) {
                conn.rollback();
            }
        } finally {
            // PreparedStatement 종료
            if( pstmt != null ) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // 결과 반환  - 입력된 데이터 건수
        return inserted;
    }

    // 데이터 수정 함수
    public int updatePerson(Map<String, Object> updateMap, boolean update) throws SQLException {

        //   - 수정 결과 변수
        int updated = 0;

        try {
            // PreparedStatement 생성
            pstmt = conn.prepareStatement(UPDATE_SQL);

            // 입력 데이터 매핑
            pstmt.setObject(1, updateMap.get("NAME"));
            pstmt.setObject(2, updateMap.get("EMAIL"));
            pstmt.setObject(3, updateMap.get("PASSWORD"));
            pstmt.setObject(4, updateMap.get("RECENT_LOGIN_DATE"));
            pstmt.setObject(5, updateMap.get("ID"));

            // 쿼리 실행
            pstmt.executeUpdate();

            // 입력건수  조회
            updated = pstmt.getUpdateCount();

            // 트랜잭션 COMMIT
            conn.commit();
            if (update) {
                System.out.println("회원 정보 수정 완료.");
                System.out.println("====================\n");
            }
        } catch (SQLException e) {
            // 오류처리
            System.out.println(e.getMessage() + "\n");
            // 오류
            updated = -1;
            // 트랜잭션 ROLLBACK
            conn.rollback();
        } finally  {
            // PreparedStatement 종료
            if( pstmt != null ) {
                try {
                    pstmt.close();
                } catch ( SQLException e ) {
                    e.printStackTrace();
                }
            }
        }

        // 결과 반환 - 수정된 데이터 건수
        return updated;
    }

    // 데이터 수정 함수
    public int updateLecture(Map<String, Object> updateMap, boolean update) throws SQLException {

        //   - 수정 결과 변수
        int updated = 0;

        try {
            // PreparedStatement 생성
            pstmt = conn.prepareStatement(UPDATE_LECTURE);

            // 입력 데이터 매핑
            pstmt.setObject(1, updateMap.get("TITLE"));
            pstmt.setObject(2, updateMap.get("LECTURER"));
            pstmt.setObject(3, updateMap.get("CATEGORY"));
            pstmt.setObject(4, updateMap.get("COUNT"));
            pstmt.setObject(5, updateMap.get("LECTURE_ID"));

            // 쿼리 실행
            pstmt.executeUpdate();

            // 입력건수  조회
            updated = pstmt.getUpdateCount();

            // 트랜잭션 COMMIT
            conn.commit();
            if (update) {
                System.out.println("강의 정보 수정 완료.");
                System.out.println("====================\n");
            }
        } catch (SQLException e) {
            // 오류처리
            System.out.println(e.getMessage() + "\n");
            // 오류
            updated = -1;
            // 트랜잭션 ROLLBACK
            conn.rollback();
        } finally  {
            // PreparedStatement 종료
            if( pstmt != null ) {
                try {
                    pstmt.close();
                } catch ( SQLException e ) {
                    e.printStackTrace();
                }
            }
        }

        // 결과 반환 - 수정된 데이터 건수
        return updated;
    }

    // 데이터 삭제 함수
    public int deletePerson(int num) throws SQLException {

        //   - 삭제 결과 변수
        int deleted = 0;

        try {
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(DELETE_SQL);

            // 조회 데이터 조건 매핑
            pstmt.setObject(1, num);

            // SQL 실행
            pstmt.executeUpdate();

            // 데이터 삭제 건수
            deleted = pstmt.getUpdateCount();

            // 트랜잭션 COMMIT
            conn.commit();
            System.out.println("회원 탈퇴 완료.");
            System.out.println("====================\n");
        } catch (SQLException e) {
            // 오류처리
            System.out.println(e.getMessage() + "\n");
            // 오류
            deleted = -1;
            // 트랜잭션 ROLLBACK
            conn.commit();
        } finally  {
            // PreparedStatement 종료
            if( pstmt != null ) {
                try {
                    pstmt.close();
                } catch ( SQLException e ) {
                    e.printStackTrace();
                }
            }
        }

        // 결과 반환 - 삭제된 데이터 건수
        return deleted;
    }

    // 데이터 삭제 함수
    public int deleteLecture(int num) throws SQLException {

        //   - 삭제 결과 변수
        int deleted = 0;

        try {
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(DELETE_LECTURE);

            // 조회 데이터 조건 매핑
            pstmt.setObject(1, num);

            // SQL 실행
            pstmt.executeUpdate();

            // 데이터 삭제 건수
            deleted = pstmt.getUpdateCount();

            // 트랜잭션 COMMIT
            conn.commit();
            System.out.println("강의 삭제 완료.");
            System.out.println("====================\n");
        } catch (SQLException e) {
            // 오류처리
            System.out.println(e.getMessage() + "\n");
            // 오류
            deleted = -1;
            // 트랜잭션 ROLLBACK
            conn.commit();
        } finally  {
            // PreparedStatement 종료
            if( pstmt != null ) {
                try {
                    pstmt.close();
                } catch ( SQLException e ) {
                    e.printStackTrace();
                }
            }
        }

        // 결과 반환 - 삭제된 데이터 건수
        return deleted;
    }

    // 데이터 삭제 함수
    public int deleteEnrollment(int id, int input, Map<String, Object> updateMap, boolean update, boolean cancel) throws SQLException {

        //   - 삭제 결과 변수
        int deleted = 0;

        try {
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(DELETE_ENROLLMENT);

            // 조회 데이터 조건 매핑
            pstmt.setObject(1, id);
            pstmt.setObject(2, input);

            // SQL 실행
            pstmt.executeUpdate();

            // 데이터 삭제 건수
            deleted = pstmt.getUpdateCount();

            // 트랜잭션 COMMIT
            conn.commit();
            updateLecture(updateMap, update);
            if (cancel) {
                System.out.println("수강 취소 완료.");
                System.out.println("====================\n");
            }
        } catch (SQLException e) {
            // 오류처리
            System.out.println(e.getMessage() + "\n");
            // 오류
            deleted = -1;
            // 트랜잭션 ROLLBACK
            conn.commit();
        } finally  {
            // PreparedStatement 종료
            if( pstmt != null ) {
                try {
                    pstmt.close();
                } catch ( SQLException e ) {
                    e.printStackTrace();
                }
            }
        }

        // 결과 반환 - 삭제된 데이터 건수
        return deleted;
    }
}