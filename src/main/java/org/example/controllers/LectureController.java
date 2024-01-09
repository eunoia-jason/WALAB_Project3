package org.example.controllers;

import org.example.models.LectureModel;
import org.example.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class LectureController {
    private final ArrayList<LectureModel> lectures = new ArrayList<>();

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
    }

    // parameter의 index의 강의 삭제
    public void deleteLecture(int id) {
        lectures.remove(id-1);
    }

    // parameter의 강의명 or 강사가 포함된 강의 검색
    public List<LectureModel> searchLectures(String keyword) {
        return lectures.stream().filter(lecture -> lecture.getTitle().contains(keyword) || lecture.getLecturer().contains(keyword)).toList();
    }

    // 유저 수강 신청
    public void addLectureToUser(UserModel user, LectureModel lecture) {
        if (user.getLectureList().stream().anyMatch(item -> item == lecture)) {
            System.out.println("이미 수강신청된 강의입니다.");
            return;
        }
        user.getLectureList().add(lecture);
        lecture.setCount(lecture.getCount() + 1);
        System.out.println("강의가 추가되었습니다.");
    }
}
