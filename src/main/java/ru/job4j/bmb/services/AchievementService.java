package ru.job4j.bmb.services;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.bmb.content.Content;
import ru.job4j.bmb.model.Achievement;
import ru.job4j.bmb.model.Award;
import ru.job4j.bmb.model.UserEvent;
import ru.job4j.bmb.repositories.AchievementRepository;
import ru.job4j.bmb.repositories.AwardRepository;
import ru.job4j.bmb.repositories.MoodLogRepository;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class AchievementService implements ApplicationListener<UserEvent> {
    private final AwardRepository awardRepository;
    private final SentContent sentContent;
    private final MoodLogRepository moodLogRepository;
    private final AchievementRepository achievementRepository;

    public AchievementService(AwardRepository awardRepository, SentContent sentContent, MoodLogRepository moodLogRepository, AchievementRepository achievementRepository) {
        this.awardRepository = awardRepository;
        this.sentContent = sentContent;
        this.moodLogRepository = moodLogRepository;
        this.achievementRepository = achievementRepository;
    }

    @Transactional
    @Override
    public void onApplicationEvent(UserEvent event) {
        var user = event.getUser();
        int days = moodLogRepository.findByUser(user).size();
        List<Award> awards = StreamSupport.stream(awardRepository.findAll().spliterator(), false)
                .filter(a -> a.getDays() <= days)
                .toList();
        var sb = new StringBuilder();
        sb.append("Вы получили следующе награды:\n");
        awards.forEach(a -> {
            achievementRepository.save(new Achievement(user, a));
            sb.append(a.getTitle()).append("\n");
        });
        var content = new Content(user.getChatId());
        content.setText(sb.toString());
        sentContent.sent(content);
    }
}
