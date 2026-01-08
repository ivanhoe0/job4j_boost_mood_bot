package ru.job4j.bmb.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.job4j.bmb.repositories.UserRepository;

@Service
public class AdviceService {
    private final UserRepository userRepository;
    private final MoodService moodService;
    private final SentContent sentContent;

    public AdviceService(UserRepository userRepository, MoodService moodService, SentContent sentContent) {
        this.userRepository = userRepository;
        this.moodService = moodService;
        this.sentContent = sentContent;
    }

    @Scheduled(fixedRateString = "${advice.alert.period}")
    public void adviceSending() {
        for (var u : userRepository.findAll()) {
            moodService.giveAdvice(u.getChatId(), u.getClientId()).ifPresent(sentContent::sent);
        }
    }
}
