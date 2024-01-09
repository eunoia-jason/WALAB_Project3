package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private Long id;
    private String email;
    private String password;
    private String name;
    private List<LectureModel> lectureList;
    private String regDate;
    private String recentLoginDate;

    public UserModel(
            Long id,
            String email,
            String password,
            String name
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;

        this.lectureList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.regDate = LocalDateTime.now().format(formatter);
        this.recentLoginDate = LocalDateTime.now().format(formatter);
    }

    @Override
    // Object를 String으로 변환 시 출력할 문자열 정의
    public String toString() {
        return "이메일: " + getEmail() + "\n이름: " + getName() + "\n가입일: " + getRegDate() + "\n최근 로그인: " + getRecentLoginDate();
    }
}
