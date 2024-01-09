package org.example.controllers;

import org.example.models.LectureModel;
import org.example.models.UserModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectureController {
    private final ArrayList<LectureModel> lectures;

    public LectureController() {
        lectures = new ArrayList<>();
        loadLecturesFromJson("files/lectureData.json");
    }

    // 첫 시작 시 파일에서 강의 정보 로드
    private void loadLecturesFromJson(String filePath) {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            JSONArray lectureArray = (JSONArray) parser.parse(reader);

            for (Object o : lectureArray) {
                JSONObject lectureObject = (JSONObject) o;
                String title = (String) lectureObject.get("title");
                String lecturer = (String) lectureObject.get("lecturer");
                String category = (String) lectureObject.get("category");
                Long count = (Long) lectureObject.get("count");
                String regDate = (String) lectureObject.get("regDate");

                lectures.add(new LectureModel(title, lecturer, category, count, regDate));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 강의명 중복 확인
    public boolean isLectureExist(String title) {
        return lectures.stream().anyMatch(lecture -> lecture.getTitle().equals(title));
    }

    // 번호 범위 확인
    public boolean isIndexExist(int id) {
        return 0 >= id || id > lectures.size();
    }

    // 입력받은 정보 강의 생성
    public void createLecture(String title, String lecturer, String category) {
        LectureModel newLecture = new LectureModel(title, lecturer, category);
        lectures.add(newLecture);
        saveLecturesToJson("files/lectureData.json");
    }

    // 전체 강의 읽어오기
    public ArrayList<LectureModel> listAllLectures() {
        return new ArrayList<>(lectures);
    }

    // parameter들이 비어 있지 않을 때, 강의 정보 업데이트
    public void updateLecture(int id, String title, String lecturer, String category) {
        if (!title.isEmpty()) lectures.get(id-1).setTitle(title);
        if (!lecturer.isEmpty()) lectures.get(id-1).setLecturer(lecturer);
        if (!category.isEmpty()) lectures.get(id-1).setCategory(category);
        saveLecturesToJson("files/lectureData.json");
    }

    // parameter의 index의 강의 삭제
    public void deleteLecture(int id) {
        lectures.remove(id-1);
        saveLecturesToJson("files/lectureData.json");
    }

    // parameter의 강의명 or 강사가 포함된 강의 검색
    public List<LectureModel> searchLectures(String keyword) {
        return lectures.stream().filter(lecture -> lecture.getTitle().contains(keyword) || lecture.getLecturer().contains(keyword)).toList();
    }

    // 유저 수강 신청
    public void addLectureToUser(UserModel user, LectureModel lecture) {
        UserController userController = new UserController();

        if (user.getLectureList().stream().anyMatch(item -> item == lecture)) {
            System.out.println("이미 수강신청된 강의입니다.");
            return;
        }
        user.getLectureList().add(lecture);
        lecture.setCount(lecture.getCount() + 1);
        saveLecturesToJson("files/lectureData.json");
        userController.saveUsersToJson("files/userData.json");
        System.out.println("강의가 추가되었습니다.");
    }

    // 모든 유저 정보 JSON 파일로 저장
    public void saveLecturesToJson(String filePath) {
        JSONArray lecturesArray = new JSONArray();
        for (LectureModel lecture : lectures) {
            JSONObject lectureJson = new JSONObject();
            lectureJson.put("title", lecture.getTitle());
            lectureJson.put("lecturer", lecture.getLecturer());
            lectureJson.put("category", lecture.getCategory());
            lectureJson.put("count", lecture.getCount());
            lectureJson.put("regDate", lecture.getRegDate());

            lecturesArray.add(lectureJson);
        }

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(lecturesArray.toJSONString());
            System.out.println("=======강의 정보가 반영되었습니다=======");
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
