package org.example.controllers;

import lombok.Getter;
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

@Getter
public class UserController {
    private final List<UserModel> users;

    public UserController() {
        users = new ArrayList<>();
        loadUsersFromJson("files/userData.json");
    }

    // 첫 시작 시 파일에서 유저 정보 로드
    private void loadUsersFromJson(String filePath) {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            JSONArray userArray = (JSONArray) parser.parse(reader);

            for (Object o : userArray) {
                JSONObject userObject = (JSONObject) o;
                Long id = (Long) userObject.get("id");
                String email = (String) userObject.get("email");
                String password = (String) userObject.get("password");
                String name = (String) userObject.get("name");
                List<LectureModel> lectureList = (List<LectureModel>) userObject.get("lectureList");
                String regDate = (String) userObject.get("regDate");
                String recentLoginDate = (String) userObject.get("recentLoginDate");

                users.add(new UserModel(id, email, password, name, lectureList, regDate, recentLoginDate));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        saveUsersToJson("files/userData.json");
    }

    // 유저 삭제
    public void removeUser(UserModel user, String email, String password) {
        user.getLectureList().forEach(lecture -> lecture.setCount(lecture.getCount()-1));
        users.removeIf(item -> item.getEmail().equals(email) && item.getPassword().equals(password));
        saveUsersToJson("files/userData.json");
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
        saveUsersToJson("files/userData.json");
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
            saveUsersToJson("files/userData.json");
            lectureController.saveLecturesToJson("files/lectureData.json");
            System.out.println("수강이 취소되었습니다.");
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
        }
    }

    // 수강 중인 강의 검색
    public List<LectureModel> searchLectures(UserModel user, String keyword) {
        return user.getLectureList().stream().filter(lecture -> lecture.getTitle().contains(keyword) || lecture.getLecturer().contains(keyword)).toList();
    }

    // 모든 유저 정보 JSON 파일로 저장
    public void saveUsersToJson(String filePath) {
        JSONArray usersArray = new JSONArray();
        for (UserModel user : users) {
            JSONObject userJson = new JSONObject();
            userJson.put("id", user.getId());
            userJson.put("email", user.getEmail());
            userJson.put("password", user.getPassword());
            userJson.put("name", user.getName());

            JSONArray lecturesArray = new JSONArray();
            for (LectureModel lecture : user.getLectureList()) {
                JSONObject lectureJson = new JSONObject();
                lectureJson.put("title", lecture.getTitle());
                lectureJson.put("lecturer", lecture.getLecturer());
                lectureJson.put("category", lecture.getCategory());
                lectureJson.put("count", lecture.getCount());
                lectureJson.put("regDate", lecture.getRegDate());
                lecturesArray.add(lectureJson);
            }
            userJson.put("lectureList", lecturesArray);
            userJson.put("regDate", user.getRegDate());
            userJson.put("recentLoginDate", user.getRecentLoginDate());

            usersArray.add(userJson);
        }

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(usersArray.toJSONString());
            System.out.println("=======유저 정보가 반영되었습니다=======");
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}