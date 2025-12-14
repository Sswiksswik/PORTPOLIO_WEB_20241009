
package com.example.demo.controller;


import com.example.demo.model.service.TestService;
import com.example.demo.model.domain.TestDB; // 최상단 도메인 클래스 연동 추가
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller
;
import org.springframework.ui.Model
;
import org.springframework.web.bind.annotation.GetMapping
;
import java.util.List;


@Controller // 컨트롤러 어노테이션 명시
public class DemoController
    {
    @GetMapping("/hello") // 전송 방식 GET
    public String hello(Model model) {
        model.addAttribute("data", " 방갑습니다."); // model 설정
        return "hello"; // hello.html 연결
    }


    
    @GetMapping("/hello2") // 전송 방식 GET
    public String hello2(Model model) {
    model.addAttribute("data", " 방갑습니다."); // model 설정
    return "hello2"; // hello.html 연결
    }

    @GetMapping("/about_detailed")
    public String about() {
    return "about_detailed";
    }

    @GetMapping("/test1")
    public String thymeleaf_test1(Model model) {
    model.addAttribute("data1", "<h2> 방갑습니다 </h2>");
    model.addAttribute("data2", "태그의 속성 값");
    model.addAttribute("link", 01);
    model.addAttribute("name", "홍길동");
    model.addAttribute("para1", "001");
    model.addAttribute("para2", 002);
    return "thymeleaf_test1";
    }
    
@Autowired
    TestService testService;

    @GetMapping("/testdb")
    public String getAllTestDBs(Model model) {

        List<TestDB> userList = testService.findAllTestDBs(); 

        model.addAttribute("users", userList); 
        
        System.out.println("데이터 목록 출력 디버그 : " + userList);
        return "testdb";
    }
}


