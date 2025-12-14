package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid; // ★ 아까 추가하기로 한 검증 도구

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.domain.Member;
import com.example.demo.model.service.AddMemberRequest;
import com.example.demo.model.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberService memberService;

    // 회원 가입 페이지 연결
    @GetMapping("/join_new")
    public String join_new() {
        return "join_new"; // join_new.html
    }


    @PostMapping("/api/members")
    public String addmembers(@Valid @ModelAttribute AddMemberRequest request) {
        memberService.saveMember(request);
        return "join_end"; // 가입 완료 페이지
    }

    // 3. 로그인 페이지 연결
    @GetMapping("/member_login")
    public String member_login() {
        return "login"; // login.html
    }

    @PostMapping("/api/login_check")
    public String checkMembers(@ModelAttribute AddMemberRequest request, Model model, HttpSession session) {
        try {
            // 서비스에서 로그인 확인 
            Member member = memberService.loginCheck(request.getEmail(), request.getPassword());
            
            
            session.setAttribute("loginMember", member.getEmail()); 
            
            // 게시판 목록으로 이동
            return "redirect:/board_list"; 

        } catch (IllegalArgumentException e) {
            // 로그인 실패 시 에러 메시지를 담아서 다시 로그인 페이지로
            model.addAttribute("error", e.getMessage());
            return "login"; // login.html
        }
    }

@GetMapping("/api/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); 
        }
        return "redirect:/member_login";
    }
@GetMapping("/board_write")
    public String board_write() {
        return "board_write";
    }
    // 6. 세션 만료 페이지
    @GetMapping("/session-expired")
    public String sessionExpired() {
        return "session_expired";
    }
}


//     String email = request.getEmail(); // 이메일 얻기
//     session.setAttribute("userId", sessionId); // 아이디 이름 설정
//     session.setAttribute("email", email); // 이메일 설정
//     model.addAttribute("member", member); // 로그인 성공 시 회원 정보 전달
//     String userId = (String) session.getAttribute("userId"); // 세션 아이디 존재 확인
//     String email = (String) session.getAttribute("email"); // 세션에서 이메일 확인

//     model.addAttribute("startNum", startNum);
//     model.addAttribute("email", email); // 로그인 사용자(이메일)

    
//     @GetMapping("/join_new")
//     public String join_new() { return "join_new"; }
//     @PostMapping("/api/members")
//     public String addmembers(@ModelAttribute AddMemberRequest request) { memberService.saveMember(request); return "join_end"; }
    
// }