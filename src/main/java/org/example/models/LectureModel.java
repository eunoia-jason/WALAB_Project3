package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LectureModel {
    private String title;
    private String lecturer;
    private String category;
    private Long count;
    private String regDate;

    public LectureModel(String title, String lecturer, String category) {
        this.title = title;
        this.lecturer = lecturer;
        this.category = category;

        this.count = 0L;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.regDate = LocalDateTime.now().format(formatter);
    }

    @Override
    // Object를 String으로 변환 시 출력할 문자열 정의
    public String toString() {
        return ". 제목: " + getTitle() + "  강사: " + getLecturer() + "  카테고리: " + getCategory() + "  수강자 수: " + getCount() + "  등록일자: " + getRegDate();
    }
}
