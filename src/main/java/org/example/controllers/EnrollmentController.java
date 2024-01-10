package org.example.controllers;

import org.example.database.DMLService;
import org.example.database.DQLService;

import java.sql.SQLException;
import java.util.HashMap;

public class EnrollmentController {
    public void register(DMLService dml, int id, int lectureID) throws SQLException {
        final HashMap<String, Object> dataMap = new HashMap<String, Object>();

        dataMap.put("USER_ID", id);
        dataMap.put("LECTURE_ID", lectureID);

        dml.insertEnrollment(dataMap);
    }

    public void getLectureList(DQLService dql, int id) {
        dql.printMapListLecture(dql.selectAllEnrollment(id));
    }
}
