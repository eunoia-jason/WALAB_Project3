package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.database.DMLService;
import org.example.database.DQLService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class UserController {
    private final EnrollmentController enrollmentController;

    public void readData(DQLService dql) throws SQLException {
        List<Map<String, Object>> resultList = dql.selectAll();
        dql.printMapList(resultList);
    }

    // 회원가입
    public void createUser(DMLService dml, String email, String password, String name) throws SQLException {
        final HashMap<String, Object> dataMap = new HashMap<>();

        dataMap.put("NAME", name);
        dataMap.put("EMAIL", email);
        dataMap.put("PASSWORD" , password);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String regDate = LocalDateTime.now().format(formatter);

        dataMap.put("RECENT_LOGIN_DATE" , regDate);
        dataMap.put("REG_DATE" , regDate);

        dml.insertPerson(dataMap);
    }

    // 유저 삭제
    public void removeUser(DQLService dql, DMLService dml, int id, String email, String password) throws SQLException {
//        user.getLectureList().forEach(lecture -> lecture.setCount(lecture.getCount()-1));
//        users.removeIf(item -> item.getEmail().equals(email) && item.getPassword().equals(password));
        Map<String, Object> user = dql.selectID(id);

        if (user.get("EMAIL").equals(email) && user.get("PASSWORD").equals(password)) {
            for (Map<String, Object> counter : dql.selectAllEnrollment(id)) {
                enrollmentController.deleteEnrollment(dml, counter, id);
            }
            dml.deletePerson(id);
        }
        else {
            System.out.println("입력이 올바르지 않습니다. 다시 확인하세요.\n");
        }
    }

    // 유저 찾기
    public Map<String, Object> findUser(DQLService dql, String email, String password) throws SQLException {
        return dql.selectByName(email, password);
    }

    // 유저 정보 수정
    public void updateUser(DQLService dql, DMLService dml, int id, String email, String password, String name) throws SQLException {
        Map<String, Object> dataMap = dql.selectID(id);

        if (!email.isEmpty()) dataMap.put("EMAIL", email);
        if (!password.isEmpty()) dataMap.put("PASSWORD", password);
        if (!name.isEmpty()) dataMap.put("NAME", name);

        dml.updatePerson(dataMap, true);
    }

    // 수강 중인 강의 검색
    public void searchLectures(DQLService dql, int id, String keyword) throws SQLException {
        dql.printMapListLecture(dql.selectByNameEnrollment(id, keyword));
    }

    public void userInfo(DQLService dql, int id) throws SQLException {
        dql.printUserInfo(dql.selectID(id));
    }

    // 현재 로그인된 유저의 수강 정보 가져오기
    public void getLectureList(DQLService dql, int id) throws SQLException {
        enrollmentController.getLectureList(dql, id);
    }

    // 현재 로그인된 유저의 수강 정보 가져오기
    public void deleteEnrollment(DQLService dql, DMLService dml, int id, int input) throws SQLException {
        enrollmentController.deleteEnrollment(dql, dml, id, input);
    }
}