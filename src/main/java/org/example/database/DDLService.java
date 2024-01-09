package org.example.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DDLService {

    final String TABLE_NAME = "USER";
    final String TABLE_LECTURE = "LECTURE";

    final String CREATE_LECTURE = "CREATE TABLE IF NOT EXISTS " + TABLE_LECTURE + " ( "
            + "  LECTURE_ID  INTEGER  PRIMARY KEY  AUTOINCREMENT, "
            + "  TITLE  VARCHAR  NOT NULL,  "
            + "  LECTURER  VARCHAR  NOT NULL,  "
            + "  CATEGORY  VARCHAR  NOT NULL,  "
            + "  COUNT INTEGER DEFAULT 0,  "
            + "  REG_DATE  TEXT  NOT NULL  )";

    final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (  "
            + "  ID  INTEGER  PRIMARY KEY  AUTOINCREMENT, "
            + "  NAME     VARCHAR     NOT NULL,  "
            + "  EMAIL     VARCHAR     NOT NULL, "
            + "  PASSWORD     VARCHAR     NOT NULL, "
            + "  RECENT_LOGIN_DATE     TEXT     NOT NULL, "
            + "  REG_DATE    TEXT     NOT NULL,  "
            + "  LECTURE_LIST    INTEGER,  "
            + "  FOREIGN KEY(LECTURE_LIST)  REFERENCES " + TABLE_LECTURE + "(LECTURE_ID))";

//    final String DROP_SQL = "DROP TABLE IF EXISTS "+ TABLE_NAME ;

    Connection conn;

    public DDLService(Connection conn) {
        this.conn = conn;
    }

    // SQL 실행 함수
    public boolean executeSQL(final String SQL) throws SQLException {

        Statement stmt = null;
        //   - 결과 변수
        boolean result = false;

        try {
            // Statement 객체  생성
            stmt = conn.createStatement();

            // SQL 실행
            stmt.execute(SQL);

            // 트랜잭션 COMMIT
            conn.commit();

            // 성공
            result = true;

        } catch (SQLException e) {
            // 오류출력
            System.out.println(e.getMessage());
            // 트랜잭션 ROLLBACK
            if( conn != null ) {
                conn.rollback();
            }
            // 오류
            result = false;

        } finally {
            // Statement 종료
            if( stmt != null ) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // 결과 반환
        return result;
    }

    // 테이블 생성 함수
    public boolean createTable() throws SQLException {
        // SQL 실행 및 반환
        return executeSQL(CREATE_SQL);
    }

    public boolean createLecture() throws SQLException {
        // SQL 실행 및 반환
        return executeSQL(CREATE_LECTURE);
    }

//    // 테이블 삭제 함수
//    public boolean dropTable(String tableName) throws SQLException {
//
//        // SQL 실행 및 반환
//        return executeSQL(DROP_SQL);
//    }

}
