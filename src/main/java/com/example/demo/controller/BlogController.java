package com.example.demo.controller; // 현재 폴더 위치
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;

// import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService; // injected service



    // @GetMapping("/article_list")
    // public String article_list(Model model) {
    //     List<Article> list = blogService.findAll();
    //     model.addAttribute("articles", list);
    //     return "article_list";
    // }

    // simple board_list (kept for compatibility) — original no-arg variant
    // @GetMapping("/board_list")
    // public String board_list(Model model) {
    //     List<Article> list = blogService.findAll();
    //     model.addAttribute("articles", list);
    //     return "board_list";
    // }

    // [수정] 아래의 페이징 기능이 있는 board_list와 URL이 중복되어 실행 오류가 발생하므로 주석 처리했습니다.
    // @GetMapping("/board_list") // 새로운 게시판 링크 지정
    // public String board_list(Model model) {
      
    //     List<Board> list = blogService.findAll(); // [수정] board -> Board (대소문자 수정)
        
    //     // HTML(board_list.html)에서 items="${articles}" 
    //     model.addAttribute("articles", list); 
        
    //     return "board_list"; // .HTML 연결
    // }

    @GetMapping("/board_list") // 새로운 게시판 링크 지정
    public String board_list(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String keyword) {
        PageRequest pageable = PageRequest.of(page, 3, org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "id")); 
        
        Page<Board> pageList;
        if (keyword.isEmpty()) {
            pageList = blogService.findAll(pageable); 
        } else {
            pageList = blogService.searchByKeyword(keyword, pageable); 
        }

        List<Board> list = pageList.getContent();
        model.addAttribute("articles", list); 
        model.addAttribute("totalPages", pageList.getTotalPages()); 
        model.addAttribute("currentPage", page); 
        model.addAttribute("keyword", keyword); 
        
        return "board_list";
    }
    

    // more feature-rich board_list with pagination/keyword/session check

    @GetMapping(value = "/board_list", params = {"page", "keyword"})
    public String board_list(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword,
            HttpSession session) {

        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/member_login";
        }
        System.out.println("세션 userId: " + userId);
        
        List<Board> boards = blogService.findAll();
        model.addAttribute("articles", boards);
        return "board_list";
    }
    

    @GetMapping("/article_edit/{id}")
    public String article_edit(Model model, @PathVariable Long id) { // 파라미터 정리
        Optional<Board> list = blogService.findById(id);
        
        if (list.isPresent()) {
            model.addAttribute("article", list.get());
            return "board_write"; 
        } else {
            return "error";
        }
        // return "redirect:/board_list"; 
    }
        
    //      @PutMapping("/api/article_edit/{id}")
    // public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
    // blogService.update(id, request);
    // return "redirect:/article_list"; // 글 수정 이후 .html 연결
    // }

    @PostMapping("/articles")
    public String addArticle(@ModelAttribute AddArticleRequest request) {
        blogService.save(request);
        // 💡 저장 후 목록 페이지로 리다이렉트
        return "redirect:/board_list";
    }

    //     @GetMapping("/board_list")
    //     public String board_list(Model model) {
    //     List<Board> list = blogService.findAll();
    //     model.addAttribute("articles", list); // 모델에 추가
    //     return "board_list"; // .HTML 연결
    // }

    

        @PutMapping("/article_edit/{id}")
        public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
            blogService.update(id, request);
            return "redirect:/board_list"; // 글 수정 이후 페이지
        }

    @GetMapping("/article_delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/board_list";
    }

    @ControllerAdvice
    public static class GlobalExceptionControllerAdvice {
        
        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public String handleTypeMismatchException(MethodArgumentTypeMismatchException ex, Model model) {
            
            
            return "error_page/article_error";
        }
    }
    // 글쓰기 게시판
    // @GetMapping("/board_write")
    // public String board_write() {
    // return "board_write";
    // }

    @GetMapping("/board_view/{id}") // 게시판 링크 지정
    public String board_view(Model model, @PathVariable Long id) {
        // [수정] Board -> Article로 변경 (저장된 데이터는 Article이니까) -> 설명과 달리 코드는 Board로 맞춰야 함
        Optional<Board> list = blogService.findById(id); // [수정] Article -> Board 로 변경
        
        if (list.isPresent()) {
            model.addAttribute("boards", list.get());
        } else {
            // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
            return "/error_page/article_error"; // 오류 처리 페이지로 연결
        }
        return "board_view"; // .HTML 연결
    }
}