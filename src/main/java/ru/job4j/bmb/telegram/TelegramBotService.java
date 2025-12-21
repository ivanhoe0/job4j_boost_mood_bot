package ru.job4j.bmb.telegram;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotService {
    @PostConstruct
    public void initialize() {
        System.out.println("Initiation of TelegramBotService bean.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Destroying of TelegramBotService bean.");
    }
}
