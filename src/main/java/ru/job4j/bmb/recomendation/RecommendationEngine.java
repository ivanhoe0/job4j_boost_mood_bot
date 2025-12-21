package ru.job4j.bmb.recomendation;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class RecommendationEngine {
    @PostConstruct
    public void initialize() {
        System.out.println("Initiation of RecommendationEngine bean.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Destroying of RecommendationEngine bean.");
    }
}
