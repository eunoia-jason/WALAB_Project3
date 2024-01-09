package org.example.controllers;

import lombok.Getter;
import org.example.models.LectureModel;
import org.example.models.UserModel;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserController {
    private final List<UserModel> users = new ArrayList<>();

    // 이메일 중복 체크
    public boolean isEmailExist(String email) {
        return users.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    // 회원가입
    public void createUser(Long id, String email, String password, String name) {
        if (isEmailExist(email)) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        UserModel newUser = new UserModel(id++, email, password, name);

        users.add(newUser);
    }

    // 유저 삭제
    public void removeUser(UserModel user, String email, String password) {
        user.getLectureList().forEach(lecture -> lecture.setCount(lecture.getCount()-1));
        users.removeIf(item -> item.getEmail().equals(email) && item.getPassword().equals(password));
    }

    // 유저 목록 조회
    public List<UserModel> listAllUsers() {
        return new ArrayList<>(users);
    }

    // 유저 찾기
    public UserModel findUser(String email, String password) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    // 유저 정보 수정
    public void updateUser(UserModel user, String email, String password, String name) {
        if (user != null) {
            if (email != null && !email.isEmpty()) user.setEmail(email);
            if (password != null && !password.isEmpty()) user.setPassword(password);
            if (name != null && !name.isEmpty()) user.setName(name);
        }
    }

    // 현재 로그인된 유저의 수강 정보 가져오기
    public void getLectureList(UserModel user) {
        for (int i=0; i<user.getLectureList().size(); i++) {
            System.out.println((i+1) + user.getLectureList().get(i).toString());
        }
    }

    // 수강 취소
    public void deleteLecture(UserModel user, int input) {
        LectureController lectureController = new LectureController();

        try {
            lectureController.listAllLectures().get(input - 1).setCount(lectureController.listAllLectures().get(input - 1).getCount() - 1);
            user.getLectureList().remove(input - 1);
            System.out.println("수강이 취소되었습니다.");
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
        }
    }

    // 수강 중인 강의 검색
    public List<LectureModel> searchLectures(UserModel user, String keyword) {
        return user.getLectureList().stream().filter(lecture -> lecture.getTitle().contains(keyword) || lecture.getLecturer().contains(keyword)).toList();
    }
}