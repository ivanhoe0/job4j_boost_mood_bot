package ru.job4j.bmb.services;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.job4j.bmb.content.Content;
import ru.job4j.bmb.model.MoodLog;
import ru.job4j.bmb.model.User;
import ru.job4j.bmb.model.UserEvent;
import ru.job4j.bmb.recomendation.RecommendationEngine;
import ru.job4j.bmb.repositories.AchievementRepository;
import ru.job4j.bmb.repositories.MoodLogRepository;
import ru.job4j.bmb.repositories.MoodRepository;
import ru.job4j.bmb.repositories.UserRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class MoodService {
    private final ApplicationEventPublisher publisher;
    private final MoodRepository moodRepository;
    private final MoodLogRepository moodLogRepository;
    private final RecommendationEngine recommendationEngine;
    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd-MM-yyyy HH:mm")
            .withZone(ZoneId.systemDefault());

    public MoodService(MoodLogRepository moodLogRepository,
                       RecommendationEngine recommendationEngine,
                       UserRepository userRepository,
                       AchievementRepository achievementRepository,
                       MoodRepository moodRepository,
                       ApplicationEventPublisher publisher) {
        this.moodLogRepository = moodLogRepository;
        this.recommendationEngine = recommendationEngine;
        this.userRepository = userRepository;
        this.achievementRepository = achievementRepository;
        this.moodRepository = moodRepository;
        this.publisher = publisher;
    }

    public Content chooseMood(User user, Long moodId) {
        var mood = moodRepository.findById(moodId);
        mood.ifPresent(value -> moodLogRepository.save(new MoodLog(user, value)));
        publisher.publishEvent(new UserEvent(this, user));
        return recommendationEngine.recommendFor(user.getChatId(), moodId);
    }

    public Optional<Content> weekMoodLogCommand(long chatId, Long clientId) {
        var user = userRepository.findByClientId(clientId);
        var moodLogs = moodLogRepository.findByUser(user).stream()
                .filter(
                moodLog -> LocalDateTime
                        .now()
                        .minusWeeks(1)
                        .isBefore(LocalDateTime
                                .ofInstant(
                        Instant.ofEpochMilli(moodLog.getCreatedAt()),
                        formatter.getZone())))
                .toList();
        var content = new Content(chatId);
        content.setText(formatMoodLogs(moodLogs, "Лог настроений за неделю"));
        return Optional.of(content);
    }

    public Optional<Content> monthMoodLogCommand(long chatId, Long clientId) {
        var user = userRepository.findByClientId(clientId);
        var moodLogs = moodLogRepository.findByUser(user).stream()
                .filter(
                        moodLog -> LocalDateTime
                                .now()
                                .minusDays(30)
                                .isBefore(LocalDateTime
                                        .ofInstant(
                                                Instant.ofEpochMilli(moodLog.getCreatedAt()),
                                                formatter.getZone())))
                .toList();
        var content = new Content(chatId);
        content.setText(formatMoodLogs(moodLogs, "Лог настроений за месяц"));
        return Optional.of(content);
    }

    private String formatMoodLogs(List<MoodLog> logs, String title) {
        if (logs.isEmpty()) {
            return title + ":\nNo mood logs found.";
        }
        var sb = new StringBuilder(title + ":\n");
        logs.forEach(log -> {
            String formattedDate = formatter.format(Instant.ofEpochSecond(log.getCreatedAt()));
            sb.append(formattedDate).append(": ").append(log.getMood().getText()).append("\n");
        });
        return sb.toString();
    }

    public Optional<Content> awards(long chatId, Long clientId) {
        var user = userRepository.findByClientId(clientId);
        var achievements = achievementRepository.findByUser(user);
        var sb = new StringBuilder("Список наград: \n");
        achievements.forEach(a -> sb.append(a.getAward().getTitle()));
        var content = new Content(chatId);
        content.setText(sb.toString());
        return Optional.of(content);
    }

    public Optional<Content> giveAdvice(long chatId, Long clientId) {
        var user = userRepository.findByClientId(clientId);
        var moodLog = moodLogRepository.findLastMoodLogOfUser(user);
        return Optional.of(recommendationEngine.adviceFor(chatId, moodLog.getMood().getId()));
    }
}
