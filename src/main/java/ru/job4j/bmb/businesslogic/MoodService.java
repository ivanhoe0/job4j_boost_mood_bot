package ru.job4j.bmb.businesslogic;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class MoodService {
    @PostConstruct
    public void initialize() {
        System.out.println("Initiation of MoodService bean.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Destroying of MoodService bean.");
    }
}
