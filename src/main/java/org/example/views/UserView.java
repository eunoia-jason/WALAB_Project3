package org.example.views;

import org.example.controllers.EnrollmentController;
import org.example.controllers.UserController;
import org.example.database.DMLService;
import org.example.database.DQLService;
import org.example.models.LectureModel;
import org.example.models.UserModel;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserView {
    private final Scanner in;
    private final UserController userController;
    private final EnrollmentController enrollmentController;

    public UserView(Scanner in, UserController userController, EnrollmentController enrollmentController) {
        this.in = in;
        this.userController = userController;
        this.enrollmentController = enrollmentController;
    }

    // 로그인
    public Map<String, Object> login(DQLService dql, DMLService dml) throws SQLException {
        System.out.println("======= 로그인 =======");
        System.out.print("이메일: ");
        String email = in.nextLine();
        System.out.print("비밀번호: ");
        String password = in.nextLine();

        Map<String, Object> user = userController.findUser(dql, email, password);
        if (user != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            user.put("RECENT_LOGIN_DATE", LocalDateTime.now().format(formatter));
            dml.updatePerson(user, false);
            System.out.println("로그인 성공!");
            System.out.println("====================\n");
            return user;
        } else {
            System.out.println("로그인 실패. 이메일 또는 비밀번호를 확인해주세요.");
            System.out.println("====================\n");
            return null;
        }
    }

    // 회원가입
    public void createUser(DMLService dml) throws SQLException {
        System.out.println("======= 회원 가입 =======");
        System.out.print("이메일: ");
        String email = in.nextLine();
        System.out.print("비밀번호: ");
        String password = in.nextLine();
        System.out.print("이름: ");
        String name = in.nextLine();
        userController.createUser(dml, email, password, name);
    }

    // 회원 탈퇴
    public void removeUser(UserModel user) {
        System.out.println("======= 회원 탈퇴 =======");
        System.out.print("이메일: ");
        String email = in.nextLine();
        System.out.print("비밀번호(입력 시 정보가 삭제됩니다.): ");
        String password = in.nextLine();

        userController.removeUser(user, email, password);
    }

    // 전체 유저 정보 로드
    public void listAllUsers(DQLService dql) {
        userController.listAllUsers(dql);
    }

    // 회원 정보 수정
    public void updateUserInfo(UserModel user) {
        System.out.println("======= 정보 수정 =======");
        System.out.println("정보 수정을 위해 새로운 정보를 입력해주세요. 변경하지 않을 항목은 엔터를 눌러 넘어가세요.");
        System.out.print("새 이메일: ");
        String newEmail = in.nextLine();
        System.out.print("새 비밀번호: ");
        String newPassword = in.nextLine();
        System.out.print("새 이름: ");
        String newName = in.nextLine();

        userController.updateUser(user, newEmail, newPassword, newName);
    }

    // 현재 로그인된 유저의 수강 정보 가져오기
    public void getLectureList(DQLService dql, int id) {
        enrollmentController.getLectureList(dql, id);
    }

    // 수강 취소
    public void deleteLecture(UserModel user) {
        System.out.println("======= 수강 취소 =======");
        userController.getLectureList(user);
        System.out.println("=======================");
        System.out.print("취소할 강의의 번호를 입력하세요: ");
        int input = in.nextInt();

        userController.deleteLecture(user, input);
    }

    // 수강 중인 강의 검색
    public void searchLecture(UserModel user) {
        System.out.print("검색할 강의명 or 강사를 입력해 주세요: ");
        String keyword = in.nextLine();

        List<LectureModel> lectures = userController.searchLectures(user, keyword);

        System.out.println("======= 수강 목록 =======");
        for (int i=0; i<lectures.size(); i++) {
            System.out.println((i+1) + lectures.get(i).toString());
        }
        System.out.println("=======================\n");
    }
}
