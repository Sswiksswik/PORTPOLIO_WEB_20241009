package com.example.demo.model.service;


import com.example.demo.model.domain.Member;
import com.example.demo.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import org.springframework.validation.annotation.Validated; 
import jakarta.validation.Valid;

@Service
@Validated
@Transactional // 트랜잭션 처리(클래스 내 모든 메소드 대상)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // 스프링 시큐리티 설정에서 등록한 BCryptPasswordEncoder 주입

    /* 회원 가입 처리*/
    public Member saveMember(@Valid AddMemberRequest request) {
        validateDuplicateMember(request); // 이메일 중복 체크
        
        String encodedPassword = passwordEncoder.encode(request.getPassword()); 
                request.setPassword(encodedPassword);
        
        return memberRepository.save(request.toEntity());
    }

    /*로그인 체크 (이메일과 비밀번호 검증)*/
    public Member loginCheck(String email, String rawPassword) {
    Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 이메일입니다."));


        if (!passwordEncoder.matches(rawPassword, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return member; // 인증 성공 시 회원 객체 반환
    }

    //중복 회원 검증 (내부 메소드)
    private void validateDuplicateMember(AddMemberRequest request) {
        // 이메일 존재 유무 확인
        memberRepository.findByEmail(request.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 가입된 회원입니다."); // 이미 존재하면 예외 발생
                });
    }
}