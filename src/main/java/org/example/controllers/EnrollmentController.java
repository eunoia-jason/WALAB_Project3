package org.example.controllers;

import org.example.database.DMLService;
import org.example.database.DQLService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EnrollmentController {
    public void register(DQLService dql, DMLService dml, int id, int lectureID) throws SQLException {
        final HashMap<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, Object> counter = dql.selectLectureID(lectureID);

        counter.put("COUNT", Integer.parseInt(counter.get("COUNT").toString()) + 1);
        counter.put("LECUTER_ID", lectureID);

        dataMap.put("USER_ID", id);
        dataMap.put("LECTURE_ID", lectureID);

        dml.updateLecture(counter, false);
        dml.insertEnrollment(dataMap);
    }

    public void getLectureList(DQLService dql, int id) {
        dql.printMapListLecture(dql.selectAllEnrollment(id));
    }
}
