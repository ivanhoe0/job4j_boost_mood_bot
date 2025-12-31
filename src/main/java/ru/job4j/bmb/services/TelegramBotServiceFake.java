package ru.job4j.bmb.services;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.job4j.bmb.conditions.FakeModeCondition;
import ru.job4j.bmb.content.Content;

@Service
@Conditional(FakeModeCondition.class)
public class TelegramBotServiceFake extends TelegramLongPollingBot implements SentContent {

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("Вызов метода onUpdateReceived");
    }

    @Override
    public String getBotUsername() {
        return "TestBOt";
    }

    @Override
    public void sent(Content content) {
        System.out.println("Вызоа метода sent");
    }
}
