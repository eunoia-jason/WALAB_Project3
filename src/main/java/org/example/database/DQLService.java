package org.example.database;

import java.sql.*;
import java.util.*;

public class DQLService {

    final String SELECTALL_SQL = "SELECT * FROM USER ";
    final String SELECTALL_LECTURE = "SELECT * FROM LECTURE ";
    final String SELECTBYNAME_SQL = "SELECT * FROM USER WHERE EMAIL = ? AND PASSWORD = ?";
    final String SELECTBYID_SQL = "SELECT * FROM USER WHERE ID = ?";
    final String SELECTBYID_LECTURE = "SELECT * FROM LECTURE WHERE LECTURE_ID = ?";
    final String SELECTBYNAME_LECTURE = "SELECT * FROM LECTURE WHERE LOWER(TITLE) LIKE LOWER(?) OR LOWER(LECTURER) LIKE LOWER(?)";
    final String SELECTALL_ENROLLMENT = "SELECT * FROM LECTURE JOIN ENROLLMENT ON LECTURE.LECTURE_ID = ENROLLMENT.LECTURE_ID WHERE ENROLLMENT.USER_ID = ?";
    final String SELECTBYNAME_ENROLLMENT = "SELECT * FROM (SELECT * FROM LECTURE JOIN ENROLLMENT ON LECTURE.LECTURE_ID = ENROLLMENT.LECTURE_ID WHERE ENROLLMENT.USER_ID = ?) WHERE LOWER(TITLE) LIKE LOWER(?) OR LOWER(LECTURER) LIKE LOWER(?)";

    Connection conn;
    PreparedStatement pstmt;
    ResultSetMetaData meta;

    public DQLService(Connection conn) {
        this.conn = conn;
    }

    // 데이터 조회 함수
    public List<Map<String, Object>> selectAll(){

        //   - 조회 결과 변수
        final Set<String> columnNames = new HashSet<String>();
        final List<Map<String, Object>> selected = new ArrayList<Map<String, Object>>();

        try {
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(SELECTALL_SQL);

            // 데이터 조회
            ResultSet rs = pstmt.executeQuery();

            // 조회된 데이터의 컬럼명 저장
            meta = pstmt.getMetaData();
            for(int i=1; i<=meta.getColumnCount(); i++) {
                columnNames.add(meta.getColumnName(i));
            }

            // ResultSet -> List<Map> 객체
            Map<String, Object> resultMap = null;

            while(rs.next()) {
                resultMap = new HashMap<String, Object>();

                for(String column : columnNames) {
                    resultMap.put(column, rs.getObject(column));
                }

                if( resultMap != null ) {
                    selected.add(resultMap);
                }
            }

        } catch (SQLException e) {
            // 오류처리
            System.out.println(e.getMessage() + "\n");

        } finally  {
            try {
                // PreparedStatement 종료
                if( pstmt != null ) {
                    pstmt.close();
                }

            } catch ( SQLException e ) {
                e.printStackTrace();
            }
        }

        // 결과 반환 - 조회된 데이터 리스트
        return selected;
    }

    public List<Map<String, Object>> selectAllLecture(){

        //   - 조회 결과 변수
        final Set<String> columnNames = new HashSet<String>();
        final List<Map<String, Object>> selected = new ArrayList<Map<String, Object>>();

        try {
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(SELECTALL_LECTURE);

            // 데이터 조회
            ResultSet rs = pstmt.executeQuery();

            // 조회된 데이터의 컬럼명 저장
            meta = pstmt.getMetaData();
            for(int i=1; i<=meta.getColumnCount(); i++) {
                columnNames.add(meta.getColumnName(i));
            }

            // ResultSet -> List<Map> 객체
            Map<String, Object> resultMap = null;

            while(rs.next()) {
                resultMap = new HashMap<String, Object>();

                for(String column : columnNames) {
                    resultMap.put(column, rs.getObject(column));
                }

                if( resultMap != null ) {
                    selected.add(resultMap);
                }
            }

        } catch (SQLException e) {
            // 오류처리
            System.out.println(e.getMessage() + "\n");

        } finally  {
            try {
                // PreparedStatement 종료
                if( pstmt != null ) {
                    pstmt.close();
                }

            } catch ( SQLException e ) {
                e.printStackTrace();
            }
        }

        // 결과 반환 - 조회된 데이터 리스트
        return selected;
    }

    // 데이터 조회 함수
    public List<Map<String, Object>> selectAllEnrollment(int id){

        //   - 조회 결과 변수
        final Set<String> columnNames = new HashSet<String>();
        final List<Map<String, Object>> selected = new ArrayList<Map<String, Object>>();

        try {
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(SELECTALL_ENROLLMENT);

            // 조회 데이터 조건 매핑
            pstmt.setObject(1, id);

            // 데이터 조회
            ResultSet rs = pstmt.executeQuery();

            // 조회된 데이터의 컬럼명 저장
            meta = pstmt.getMetaData();
            for(int i=1; i<=meta.getColumnCount(); i++) {
                columnNames.add(meta.getColumnName(i));
            }

            // ResultSet -> List<Map> 객체
            Map<String, Object> resultMap = null;

            while(rs.next()) {
                resultMap = new HashMap<String, Object>();

                for(String column : columnNames) {
                    resultMap.put(column, rs.getObject(column));
                }

                if( resultMap != null ) {
                    selected.add(resultMap);
                }
            }

        } catch (SQLException e) {
            // 오류처리
            System.out.println(e.getMessage() + "\n");

        } finally  {
            try {
                // PreparedStatement 종료
                if( pstmt != null ) {
                    pstmt.close();
                }

            } catch ( SQLException e ) {
                e.printStackTrace();
            }
        }

        // 결과 반환 - 조회된 데이터 리스트
        return selected;
    }

    public Map<String, Object> selectID(int id){

        //   - 조회 결과 변수
        final Set<String> columnNames = new HashSet<String>();
        final List<Map<String, Object>> selected = new ArrayList<Map<String, Object>>();

        try {
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(SELECTBYID_SQL);

            // 조회 데이터 조건 매핑
            pstmt.setObject(1, id);

            // 데이터 조회
            ResultSet rs = pstmt.executeQuery();

            // 조회된 데이터의 컬럼명 저장
            meta = pstmt.getMetaData();
            for(int i=1; i<=meta.getColumnCount(); i++) {
                columnNames.add(meta.getColumnName(i));
            }

            // ResultSet -> List<Map> 객체
            Map<String, Object> resultMap = null;

            while(rs.next()) {
                resultMap = new HashMap<String, Object>();

                for(String column : columnNames) {
                    resultMap.put(column, rs.getObject(column));
                }

                if( resultMap != null ) {
                    selected.add(resultMap);
                }
            }

        } catch (SQLException e) {
            // 오류처리
            System.out.println(e.getMessage() + "\n");

        } finally  {
            try {
                // PreparedStatement 종료
                if( pstmt != null ) {
                    pstmt.close();
                }

            } catch ( SQLException e ) {
                e.printStackTrace();
            }
        }

        // 결과 반환 - 조회된 데이터 리스트
        return selected.get(0);
    }

    public Map<String, Object> selectLectureID(int id){

        //   - 조회 결과 변수
        final Set<String> columnNames = new HashSet<String>();
        final List<Map<String, Object>> selected = new ArrayList<Map<String, Object>>();

        try {
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(SELECTBYID_LECTURE);

            // 조회 데이터 조건 매핑
            pstmt.setObject(1, id);

            // 데이터 조회
            ResultSet rs = pstmt.executeQuery();

            // 조회된 데이터의 컬럼명 저장
            meta = pstmt.getMetaData();
            for(int i=1; i<=meta.getColumnCount(); i++) {
                columnNames.add(meta.getColumnName(i));
            }

            // ResultSet -> List<Map> 객체
            Map<String, Object> resultMap = null;

            while(rs.next()) {
                resultMap = new HashMap<String, Object>();

                for(String column : columnNames) {
                    resultMap.put(column, rs.getObject(column));
                }

                if( resultMap != null ) {
                    selected.add(resultMap);
                }
            }

        } catch (SQLException e) {
            // 오류처리
            System.out.println(e.getMessage() + "\n");

        } finally  {
            try {
                // PreparedStatement 종료
                if( pstmt != null ) {
                    pstmt.close();
                }

            } catch ( SQLException e ) {
                e.printStackTrace();
            }
        }

        // 결과 반환 - 조회된 데이터 리스트
        return selected.get(0);
    }

    // 데이터 조회 함수
    public Map<String, Object> selectByName(String email, String password){

        //   - 조회 결과 변수
        final Set<String> columnNames = new HashSet<String>();
        final List<Map<String, Object>> selected = new ArrayList<Map<String, Object>>();

        try {
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(SELECTBYNAME_SQL);

            // 조회 데이터 조건 매핑
            pstmt.setObject(1, email);
            pstmt.setObject(2, password);

            // 데이터 조회
            ResultSet rs = pstmt.executeQuery();

            // 조회된 데이터의 컬럼명 저장
            meta = pstmt.getMetaData();
            for(int i=1; i<=meta.getColumnCount(); i++) {
                columnNames.add(meta.getColumnName(i));
            }

            // ResultSet -> List<Map> 객체
            Map<String, Object> resultMap = null;

            while(rs.next()) {
                resultMap = new HashMap<String, Object>();

                for(String column : columnNames) {
                    resultMap.put(column, rs.getObject(column));
                }

                if( resultMap != null ) {
                    selected.add(resultMap);
                }
            }

        } catch (SQLException e) {
            // 오류처리
            System.out.println(e.getMessage() + "\n");

        } finally  {
            try {
                // PreparedStatement 종료
                if( pstmt != null ) {
                    pstmt.close();
                }

            } catch ( SQLException e ) {
                e.printStackTrace();
            }
        }

        // 결과 반환 - 조회된 데이터 리스트
        return selected.get(0);
    }

    // 데이터 조회 함수
    public List<Map<String, Object>> selectByNameLecture(String keyword){

        //   - 조회 결과 변수
        final Set<String> columnNames = new HashSet<String>();
        final List<Map<String, Object>> selected = new ArrayList<Map<String, Object>>();

        try {
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(SELECTBYNAME_LECTURE);

            // 조회 데이터 조건 매핑
            pstmt.setObject(1, "%"+ keyword +"%");
            pstmt.setObject(2, "%"+ keyword +"%");

            // 데이터 조회
            ResultSet rs = pstmt.executeQuery();

            // 조회된 데이터의 컬럼명 저장
            meta = pstmt.getMetaData();
            for(int i=1; i<=meta.getColumnCount(); i++) {
                columnNames.add(meta.getColumnName(i));
            }

            // ResultSet -> List<Map> 객체
            Map<String, Object> resultMap = null;

            while(rs.next()) {
                resultMap = new HashMap<String, Object>();

                for(String column : columnNames) {
                    resultMap.put(column, rs.getObject(column));
                }

                if( resultMap != null ) {
                    selected.add(resultMap);
                }
            }

        } catch (SQLException e) {
            // 오류처리
            System.out.println(e.getMessage());

        } finally  {
            try {
                // PreparedStatement 종료
                if( pstmt != null ) {
                    pstmt.close();
                }

            } catch ( SQLException e ) {
                e.printStackTrace();
            }
        }

        // 결과 반환 - 조회된 데이터 리스트
        return selected;
    }

    // 데이터 조회 함수
    public List<Map<String, Object>> selectByNameEnrollment(int id, String keyword){

        //   - 조회 결과 변수
        final Set<String> columnNames = new HashSet<String>();
        final List<Map<String, Object>> selected = new ArrayList<Map<String, Object>>();

        try {
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(SELECTBYNAME_ENROLLMENT);

            // 조회 데이터 조건 매핑
            pstmt.setObject(1, id);
            pstmt.setObject(2, "%"+ keyword +"%");
            pstmt.setObject(3, "%"+ keyword +"%");

            // 데이터 조회
            ResultSet rs = pstmt.executeQuery();

            // 조회된 데이터의 컬럼명 저장
            meta = pstmt.getMetaData();
            for(int i=1; i<=meta.getColumnCount(); i++) {
                columnNames.add(meta.getColumnName(i));
            }

            // ResultSet -> List<Map> 객체
            Map<String, Object> resultMap = null;

            while(rs.next()) {
                resultMap = new HashMap<String, Object>();

                for(String column : columnNames) {
                    resultMap.put(column, rs.getObject(column));
                }

                if( resultMap != null ) {
                    selected.add(resultMap);
                }
            }

        } catch (SQLException e) {
            // 오류처리
            System.out.println(e.getMessage());

        } finally  {
            try {
                // PreparedStatement 종료
                if( pstmt != null ) {
                    pstmt.close();
                }

            } catch ( SQLException e ) {
                e.printStackTrace();
            }
        }

        // 결과 반환 - 조회된 데이터 리스트
        return selected;
    }

    // 조회 결과 출력 함수
    public void printMapList(List<Map<String, Object>> mapList) {

        if(mapList.isEmpty()) {
            System.out.println("조회된 데이터가 없습니다.\n");
            return;
        }

        // 상세 데이터 출력
        System.out.println("===================== 회원 목록 =====================");
        for (Map<String, Object> map : mapList) {
            int id = Integer.parseInt(map.get("ID").toString());
            String name = (String) map.get("NAME");
            String email = (String) map.get("EMAIL");
            String recent_login_date = (String) map.get("RECENT_LOGIN_DATE");
            String reg_date = (String) map.get("REG_DATE");

            System.out.println(id + ". 이름: " + name + "  이메일: " + email + "  최근 로그인: " + recent_login_date + "  가입일: " + reg_date);
        }
        System.out.println("===================================================\n");
    }

    // 조회 결과 출력 함수
    public void printMapListLecture(List<Map<String, Object>> mapList) {

        if(mapList.isEmpty()) {
            System.out.println("조회된 데이터가 없습니다.\n");
            return;
        }

        // 상세 데이터 출력
        System.out.println("===================== 강의 목록 =====================");
        for (Map<String, Object> map : mapList) {
            int id = Integer.parseInt(map.get("LECTURE_ID").toString());
            String title = (String) map.get("TITLE");
            String lecturer = (String) map.get("LECTURER");
            int count = Integer.parseInt(map.get("COUNT").toString());
            String reg_date = (String) map.get("REG_DATE");

            System.out.println(id + ". 제목: " + title + "  강사: " + lecturer + "  수강자 수: " + count + "  등록일: " + reg_date);
        }
        System.out.println("===================================================\n");
    }

    public void printUserInfo(Map<String, Object> map) {
        System.out.println("===================== 유저 정보 =====================");
        int id = Integer.parseInt(map.get("ID").toString());
        String name = (String) map.get("NAME");
        String email = (String) map.get("EMAIL");
        String recent_login_date = (String) map.get("RECENT_LOGIN_DATE");
        String reg_date = (String) map.get("REG_DATE");
        System.out.println("아이디: " + id);
        System.out.println("이름: " + name);
        System.out.println("이메일: " + email);
        System.out.println("최근 로그인: " + recent_login_date);
        System.out.println("가입일: " + reg_date);
        System.out.println("===================================================\n");
    }
}