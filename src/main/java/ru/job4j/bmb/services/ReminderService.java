package ru.job4j.bmb.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.job4j.bmb.content.Content;
import ru.job4j.bmb.repositories.UserRepository;

@Service
public class ReminderService {
    private final TelegramBotService tgRemoteService;
    private final UserRepository userRepository;

    public ReminderService(TelegramBotService tgRemoteService, UserRepository userRepository) {
        this.tgRemoteService = tgRemoteService;
        this.userRepository = userRepository;
    }

    @Scheduled(fixedRateString = "${remind.period}")
    public void ping() {
        for (var user : userRepository.findAll()) {
            var content = new Content(user.getChatId());
            content.setText("Ping");
            tgRemoteService.sent(content);
        }
    }
}
