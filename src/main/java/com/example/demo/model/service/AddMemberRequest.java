package com.example.demo.model.service;

import lombok.*;
import com.example.demo.model.domain.Member;
import jakarta.validation.constraints.*; // 유효성 검사 어노테이션

@Getter 
@Setter
public class AddMemberRequest {

    @NotBlank // 공백 x
    private String name;

    @NotBlank
    @Email
    private String email;

    // 8글자이상, 대소문자 포함
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    // 패턴 o, 19이상~90세 이하
    @Pattern(regexp = "^(1[9]|[2-8][0-9]|90)$", message = "나이는 19세 이상 90세 이하여야 합니다.")
    private String age;

    private String mobile; 
    private String address ;

    public Member toEntity(){ 
        return Member.builder()
            .name(name)
            .email(email)
            .password(password)
            .age(age)
            .mobile(mobile)
            .address(address)
            .build();
    }
}