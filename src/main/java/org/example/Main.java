package org.example;

import org.example.controllers.LectureController;
import org.example.controllers.UserController;
import org.example.database.DDLService;
import org.example.database.DMLService;
import org.example.database.DQLService;
import org.example.database.SQLiteManager;
import org.example.models.UserModel;
import org.example.views.LectureView;
import org.example.views.UserView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner in = new Scanner(System.in);
        Connection conn = SQLiteManager.getConnection();
        LectureController lectureController = new LectureController();
        UserController userController = new UserController();
        LectureView lectureView = new LectureView(in, lectureController);
        UserView userView = new UserView(in, userController);

        DDLService DDL = new DDLService(conn);
        DMLService DML = new DMLService(conn);
        DQLService DQL = new DQLService(conn);

        DDL.createLecture();
        DDL.createTable();

        Map<String, Object> loggedInUser = null;

        while (true) {
            if (loggedInUser == null) {
                System.out.println("[1]강의 목록 [2]강의 검색 [3]로그인 [4]회원가입 [0]종료");
                int select = in.nextInt();
                in.nextLine();

                switch (select) {
                    case 1 -> lectureView.listAllLectures(DQL);
                    case 2 -> lectureView.searchLectures(DQL);
                    case 3 -> loggedInUser = userView.login(DQL, DML);
                    case 4 -> userView.createUser(DML);
                    case 0 -> {
                        System.out.println("프로그램을 종료합니다.");
                        in.close();
                        return;
                    }
                    default -> System.out.println("다시 입력해 주세요.");
                }
            } else {
                if (loggedInUser.get("EMAIL").equals("admin")) {
                    System.out.println("[1]강의 추가 [2]강의 목록 [3]강의 검색 [4]강의 수정 [5]강의 삭제 [6]회원 목록 [7]로그아웃");
                    int choice = in.nextInt();
                    in.nextLine();

                    switch (choice) {
                        case 1 -> lectureView.createLecture(DML);
                        case 2 -> lectureView.listAllLectures(DQL);
                        case 3 -> lectureView.searchLectures(DQL);
                        case 4 -> lectureView.updateLecture(DQL, DML);
                        case 5 -> lectureView.deleteLecture(DQL, DML);
                        case 6 -> userView.listAllUsers(DQL);
                        case 7 -> {
                            loggedInUser = null;
                            System.out.println("===== 로그아웃 되었습니다 =====");
                        }
                        default -> System.out.println("다시 입력해 주세요.");
                    }
                } else {
                    System.out.println("[1]수강 신청 [2]수강 목록 [3]수강 강의 검색 [4]수강 취소 [5]회원정보 [6]회원정보 수정 [7]로그아웃 [8]회원 탈퇴");
                    int choice = in.nextInt();
                    in.nextLine();

                    switch (choice) {
//                        case 1 -> lectureView.addLectureToUser(loggedInUser);
//                        case 2 -> userView.getLectureList(loggedInUser);
//                        case 3 -> userView.searchLecture(loggedInUser);
//                        case 4 -> userView.deleteLecture(loggedInUser);
                        case 5 -> System.out.println(loggedInUser);
//                        case 6 -> userView.updateUserInfo(loggedInUser);
                        case 7 -> {
                            loggedInUser = null;
                            System.out.println("===== 로그아웃 되었습니다 =====");
                        }
                        case 8 -> {
//                            userView.removeUser(loggedInUser);
                            loggedInUser = null;
                        }
                        default -> System.out.println("다시 입력해 주세요.");
                    }
                }
            }
        }
    }
}