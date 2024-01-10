package org.example.views;

import org.example.controllers.LectureController;
import org.example.database.DMLService;
import org.example.database.DQLService;
import org.example.models.LectureModel;
import org.example.models.UserModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class LectureView {
    private final Scanner in;
    private final LectureController lectureController;

    public LectureView(Scanner in, LectureController lectureController) {
        this.in = in;
        this.lectureController = lectureController;
    }

    // 강의 생성
    public void createLecture(DMLService dml) throws SQLException {
        System.out.println("======= 강의 등록 =======");
        System.out.print("강의 제목: ");
        String title = in.nextLine();
        System.out.print("강사: ");
        String lecturer = in.nextLine();
        System.out.print("카테고리: ");
        String category = in.nextLine();
        lectureController.createLecture(dml, title, lecturer, category);
    }

    // 전체 강의 읽어오기
    public void listAllLectures(DQLService dql) {
        lectureController.readData(dql);
    }

    // 강의 정보 업데이트
    public void updateLecture(DQLService dql, DMLService dml) throws SQLException {
        dql.printMapListLecture(dql.selectAllLecture());
        System.out.print("수정할 강의 번호를 선택해 주세요: ");
        int id = in.nextInt();
        in.nextLine();

        System.out.print("새 제목: ");
        String newTitle = in.nextLine();
        System.out.print("새 강사: ");
        String newLecturer = in.nextLine();
        System.out.print("새 카테고리: ");
        String newCategory = in.nextLine();

        lectureController.updateLecture(dql, dml, id, newTitle, newLecturer, newCategory);
    }

    // 강의 삭제
    public void deleteLecture(DQLService dql, DMLService dml) throws SQLException {
        dql.printMapListLecture(dql.selectAllLecture());
        System.out.print("삭제할 강의 번호를 선택해 주세요: ");
        int id = in.nextInt();
        in.nextLine();

        lectureController.deleteLecture(dml, id);
    }

    // 강의명으로 검색
    public void searchLectures(DQLService dql) {
        System.out.print("검색할 강의명 or 강사를 입력해 주세요: ");
        String keyword = in.nextLine();

        lectureController.searchLectures(dql, keyword);
    }

    // 현재 로그인된 유저 수강 신청
    public void addLectureToUser(UserModel user) {
        ArrayList<LectureModel> lectures = lectureController.listAllLectures();

        System.out.println("======= 강의 목록 =======");
        for (int i=0; i<lectures.size(); i++) {
            System.out.println((i+1) + lectures.get(i).toString());
        }
        System.out.println("=======================\n");
        System.out.print("강의의 번호를 입력하세요: ");
        int input = in.nextInt();

        try {
            LectureModel selectedLecture = lectureController.listAllLectures().get(input - 1);

            lectureController.addLectureToUser(user, selectedLecture);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
        }
    }
}
