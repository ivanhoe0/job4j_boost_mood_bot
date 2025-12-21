package ru.job4j.bmb.businesslogic;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class AchievementService {
    @PostConstruct
    public void initialize() {
        System.out.println("Initiation of AchievementService bean.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Destroying of AchievementService bean.");
    }
}
