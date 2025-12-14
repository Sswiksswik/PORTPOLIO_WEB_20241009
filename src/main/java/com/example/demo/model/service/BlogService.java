package com.example.demo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable; 
import org.springframework.data.domain.Sort;
// import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;
import com.example.demo.model.repository.BlogRepository;
import com.example.demo.model.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
public class BlogService {
    
    private final BlogRepository blogRepository;
    private final BoardRepository boardRepository;

    // 블로그 글 저장
    public Board save(AddArticleRequest request) {
        // [수정됨] Board DB에 저장되도록 Board 객체 생성 후 저장 (빈 값 방지 포함)
        return boardRepository.save(Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user("guest") // 에러 방지용 기본값
                .newdate(String.valueOf(java.time.LocalDate.now()))
                .count("0") 
                .likec("0")
                .build());
    }


    // 게시판 특정 글 조회 (상세 보기)
    public Optional<Board> findById(Long id) { 
        return boardRepository.findById(id);
    }

    // public List<Board> searchByKeyword(String keyword, Pageable pageable) {
    //     Page<Board> page = boardRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    //     return page.getContent();
    // }
    
    // 컨트롤러에서 .getTotalPages()를 쓰기 때문에 Page<Board>로 반환해야 함
    public Page<Board> searchByKeyword(String keyword, Pageable pageable) {
        return boardRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }


    // 블로그 글 수정
    public void update(Long id, AddArticleRequest request) {
        Optional<Board> optionalBoard = boardRepository.findById(id); // Board DB에서 조회
        optionalBoard.ifPresent(board -> { // 값이 있으면
            board.update(request.getTitle(), request.getContent()); // Board 값 수정
            boardRepository.save(board); // 수정된 게시글 저장
        });
    }


    // 블로그 글 삭제
    public void delete(Long id) {
        boardRepository.deleteById(id); 
    }


    // 게시판 전체 목록 조회 (기본)
    public List<Board> findAll() {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'findAll'");
        return boardRepository.findAll();
    }

    // 게시판 전체 목록 조회 (정렬 기능 포함)
    public List<Board> findAll(Sort sort) {
        return boardRepository.findAll(sort);
    }

    // 페이징 처리된 목록 조회
    public Page<Board> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

}

    // public List<Article> findAll() { 
    //  return blogRepository.findAll();
    // }
    

    // public List<Board> findAll() {
    //     return boardRepository.findAll(pageable);
    // }
    // public List<Board> findAll(Pageable pageable) {
    //     Page<Board> page = boardRepository.findAll(pageable);
    //     return page.getContent();
    // }


    // public Optional<Board> findById(Long id) { 
    //    return boardRepository.findById(id);
    // }

    // 페이징 처리된 목록 조회
    // public Page<Board> findAll(Pageable pageable) {
    //     return boardRepository.findAll(pageable);
    // }

    // 키워드 검색
    // public List<Board> searchByKeyword(String keyword, Pageable pageable) {
    //     return boardRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    // }

    // public void update(Long id, AddArticleRequest request) {
    //  Article article = blogRepository.findById(id)
    //          .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다. id=" + id));
    //  article.update(request.getTitle(), request.getContent());
    //  blogRepository.save(article);
    // }

