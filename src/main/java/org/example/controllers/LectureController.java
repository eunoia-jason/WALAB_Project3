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
public class LectureController {
    private final EnrollmentController enrollmentController;

    public void readData(DQLService dql) {
        List<Map<String, Object>> resultList = dql.selectAllLecture();
        dql.printMapListLecture(resultList);
    }

    // 입력받은 정보 강의 생성
    public void createLecture(DMLService dml, String title, String lecturer, String category) throws SQLException {
        final HashMap<String, Object> dataMap = new HashMap<String, Object>();

        dataMap.put("TITLE", title);
        dataMap.put("LECTURER", lecturer);
        dataMap.put("CATEGORY" , category);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String regDate = LocalDateTime.now().format(formatter);

        dataMap.put("REG_DATE" , regDate);

        dml.insertLecture(dataMap);
    }

    // parameter들이 비어 있지 않을 때, 강의 정보 업데이트
    public void updateLecture(DQLService dql, DMLService dml, int id, String title, String lecturer, String category) throws SQLException {
        Map<String, Object> dataMap = dql.selectLectureID(id);

        if (!title.isEmpty()) dataMap.put("TITLE", title);
        if (!lecturer.isEmpty()) dataMap.put("LECTURER", lecturer);
        if (!category.isEmpty()) dataMap.put("CATEGORY" , category);

        dml.updateLecture(dataMap, true);
    }

    // parameter의 index의 강의 삭제
    public void deleteLecture(DMLService dml, int id) throws SQLException {
        dml.deleteLecture(id);
    }

    // parameter의 강의명 or 강사가 포함된 강의 검색
    public void searchLectures(DQLService dql, String keyword) {
        List<Map<String, Object>> resultList = dql.selectByNameLecture(keyword);
        dql.printMapListLecture(resultList);
    }

    public void addLectureToUser(DQLService dql, DMLService dml, int id, int input) throws SQLException {
        enrollmentController.register(dql, dml, id, input);
    }
}
