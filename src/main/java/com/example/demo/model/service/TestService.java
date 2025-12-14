package com.example.demo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.domain.TestDB;
import com.example.demo.model.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service 
@RequiredArgsConstructor 
public class TestService { 
    @Autowired // 객체 의존성 주입
    private TestRepository testRepository;

    public TestDB findByName(String name) { 
        return (TestDB) testRepository.findByName(name);
    }

    public List<TestDB> findAll() { 
        return testRepository.findAll();
    }
    public List<TestDB> findAllTestDBs() { 
        return testRepository.findAll(); // 기존 findAll() 로직을 재사용
    }
}