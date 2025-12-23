package ru.job4j.bmb.services;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Service;

@Service
public class MoodService implements BeanNameAware {
    @PostConstruct
    public void initialize() {
        System.out.println("Initiation of MoodService bean.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Destroying of MoodService bean.");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println(name);
    }
}
