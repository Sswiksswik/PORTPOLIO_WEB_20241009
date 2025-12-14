package com.example.demo.model.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.demo.model.domain.Article;

@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드 생성자
@Getter // 값 꺼내기
@Setter // 값 설정하기
public class AddArticleRequest {

    private String title;
    private String content;
    private String user;
    private String newdate;
    private String count;
    private String likec;

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .user(user)
                .newdate(newdate)
                .count(count)
                .likec(likec)
                .build();
    }
}
// package com.example.demo.model.service;

// import javax.swing.border.Border;

// import com.example.demo.model.domain.Article;
// import com.example.demo.model.domain.Board;

// import lombok.*; // 어노테이션 자동 생성


// @Data
// @Builder
// @NoArgsConstructor // 기본 생성자 추가
// @AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
    
// public class AddArticleRequest {
//     private String title;
//     private String content;
//     private String user;
//     private String newdate;
//     private String count;
//     private String likec;

//         public Article toEntity(){ // Article 객체 생성
//             return Article.builder()
//             .name(name)
//             .email(email)
//             .password(password)
//             .age(age)
//             .mobile(mobile)
//             .address(address)
//             .build();
//         }
//  }





//         public Board toEntity(){ // 보드 객체 생성
//             return Board.builder()
//                 .title(title)
//                 .content(content)
//                 .user(user)
//                 .newdate(newdate)
//                 .count(count)
//                 .likec(likec)
//                 .build();
//         }
// }

