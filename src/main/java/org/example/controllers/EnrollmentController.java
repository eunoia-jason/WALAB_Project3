package org.example.controllers;

import lombok.Getter;
import org.example.database.DMLService;
import org.example.database.DQLService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Getter
public class EnrollmentController {
    public void register(DQLService dql, DMLService dml, int id, int lectureID) throws SQLException {
        Map<String, Object> counter = dql.selectLectureID(lectureID);

        counter.put("COUNT", Integer.parseInt(counter.get("COUNT").toString()) + 1);
        counter.put("LECUTER_ID", lectureID);

        dml.insertEnrollment(id, lectureID, counter, false);
    }

    public void getLectureList(DQLService dql, int id) {
        dql.printMapListLecture(dql.selectAllEnrollment(id));
    }

    public void deleteEnrollment(DQLService dql, DMLService dml, int id, int lectureID) throws SQLException {
        Map<String, Object> counter = dql.selectLectureID(lectureID);

        counter.put("COUNT", Integer.parseInt(counter.get("COUNT").toString()) - 1);
        counter.put("LECUTER_ID", lectureID);

        dml.deleteEnrollment(id, lectureID, counter, false, true);
    }

    public void deleteEnrollment(DMLService dml, Map<String, Object> counter, int id) throws SQLException {
        counter.put("COUNT", Integer.parseInt(counter.get("COUNT").toString()) - 1);

        dml.deleteEnrollment(id, Integer.parseInt(counter.get("LECTURE_ID").toString()), counter, false, false);
    }
}
