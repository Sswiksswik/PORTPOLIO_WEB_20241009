package com.example.demo.model.domain;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Entity
@Table(name = "Board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content; // 초기값 설정 제거
    
    // 추가하려던 필드들을 클래스 내부로 통합
    @Column(name = "user") 
    private String user;
    
    @Column(name = "newdate")
    private String newdate;
    
    @Column(name = "count")
    private String count;
    
    @Column(name = "likec")
    private String likec;

    @Builder
    public Board(String title, String content, String user, String newdate, String count, String likec) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.newdate = newdate;
        this.count = count;
        this.likec = likec;
    }
    public void update(String title, String content) { // 현재 객체 상태 업데이트
        this.title = title;
        this.content = content;
    }

    // public static Object builder() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'builder'");
    // }
    
    // update 메소드에 추가된 필드를 반영하여 오버로드 (선택적)
    // public void update(String title, String content, String user) {
    //     this.title = title;
    //     this.content = content;
    //     this.user = user;
    //     this.newdate = newdate;
    //     this.count = count;
        
    // }
}
