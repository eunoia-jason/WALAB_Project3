package org.example.views;

import lombok.AllArgsConstructor;
import org.example.controllers.UserController;
import org.example.database.DMLService;
import org.example.database.DQLService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

@AllArgsConstructor
public class UserView {
    private final Scanner in;
    private final UserController userController;

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
    public void removeUser(DQLService dql, DMLService dml, int id) throws SQLException {
        System.out.println("======= 회원 탈퇴 =======");
        System.out.print("이메일: ");
        String email = in.nextLine();
        System.out.print("비밀번호(입력 시 정보가 삭제됩니다.): ");
        String password = in.nextLine();

        userController.removeUser(dql, dml, id, email, password);
    }

    // 전체 유저 정보 로드
    public void listAllUsers(DQLService dql) throws SQLException {
        userController.readData(dql);
    }

    // 회원 정보 수정
    public void updateUserInfo(DQLService dql, DMLService dml, int id) throws SQLException {
        System.out.println("======= 정보 수정 =======");
        System.out.println("정보 수정을 위해 새로운 정보를 입력해주세요. 변경하지 않을 항목은 엔터를 눌러 넘어가세요.");
        System.out.print("새 이메일: ");
        String newEmail = in.nextLine();
        System.out.print("새 비밀번호: ");
        String newPassword = in.nextLine();
        System.out.print("새 이름: ");
        String newName = in.nextLine();

        userController.updateUser(dql, dml, id, newEmail, newPassword, newName);
    }

    // 현재 로그인된 유저의 수강 정보 가져오기
    public void getLectureList(DQLService dql, int id) throws SQLException {
        userController.getLectureList(dql, id);
    }

    // 수강 취소
    public void deleteEnrollment(DQLService dql, DMLService dml, int id) throws SQLException {
        dql.printMapListLecture(dql.selectAllEnrollment(id));
        System.out.print("취소할 강의의 번호를 입력하세요: ");
        int input = in.nextInt();

        userController.deleteEnrollment(dql, dml, id, input);
    }

    // 수강 중인 강의 검색
    public void searchLecture(DQLService dql, int id) throws SQLException {
        System.out.print("검색할 강의명 or 강사를 입력해 주세요: ");
        String keyword = in.nextLine();

        userController.searchLectures(dql, id, keyword);
    }

    public void userInfo(DQLService dql, int id) throws SQLException {
        userController.userInfo(dql, id);
    }
}
