package ru.job4j.bmb.services;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Service;

@Service
public class AchievementService implements BeanNameAware {

    @PostConstruct
    public void initialize() {
        System.out.println("Initiation of AchievementService bean.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Destroying of AchievementService bean.");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println(name);
    }
}
