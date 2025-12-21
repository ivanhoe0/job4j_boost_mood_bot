package ru.job4j.bmb.businesslogic;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class ReminderService {
    @PostConstruct
    public void initialize() {
        System.out.println("Initiation of ReminderService bean.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Destroying of ReminderService bean.");
    }
}
