package ru.job4j.bmb.recomendation;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.job4j.bmb.content.Content;
import ru.job4j.bmb.content.ContentProvider;
import ru.job4j.bmb.content.ContentProviderText;

import java.util.List;
import java.util.Random;

@Service
public class RecommendationEngine {
    private final List<ContentProvider> contents;
    private static final Random RND = new Random(System.currentTimeMillis());
    private final List<ContentProviderText> advices;

    public RecommendationEngine(List<ContentProvider> contents, List<ContentProviderText> advices) {
        this.contents = contents;
        this.advices = advices;
    }

    public Content recommendFor(Long chatId, Long moodId) {
        var index = RND.nextInt(0, contents.size());
        return contents.get(index).byMood(chatId, moodId);
    }

    public Content adviceFor(Long chatId, Long moodId) {
        var index = RND.nextInt(0, advices.size());
        return advices.get(index).byMood(chatId, moodId);
    }
}
