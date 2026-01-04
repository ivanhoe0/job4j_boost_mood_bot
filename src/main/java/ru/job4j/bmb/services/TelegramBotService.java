package ru.job4j.bmb.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.job4j.bmb.conditions.RealModeCondition;
import ru.job4j.bmb.content.Content;

@Service
@Conditional(RealModeCondition.class)
public class TelegramBotService extends TelegramLongPollingBot implements SentContent {
    private final BotCommandHandler handler;
    private final String botName;

    public TelegramBotService(@Value("${telegram.bot.name}") String botName,
                              @Value("${telegram.bot.token}") String botToken,
                              BotCommandHandler handler) {
        super(botToken);
        this.handler = handler;
        this.botName = botName;
    }

    @PostConstruct
    public void printName() {
        System.out.println("Создание бина TelegramBotService");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            handler.handleCallback(update.getCallbackQuery())
                    .ifPresent(this::sent);
        } else if (update.hasMessage() && update.getMessage().getText() != null) {
            handler.commands(update.getMessage())
                    .ifPresent(this::sent);
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void sent(Content content) {
        try {
            if (content.getAudio() != null) {
                var sendAudio = new SendAudio(String.valueOf(content.getChatId()), content.getAudio());
                if (content.getText() != null) {
                    sendAudio.setCaption(content.getText());
                }
                execute(sendAudio);
            } else if (content.getPhoto() != null) {
                var sendPhoto = new SendPhoto(String.valueOf(content.getChatId()), content.getPhoto());
                if (content.getText() != null) {
                    sendPhoto.setCaption(content.getText());
                }
                execute(sendPhoto);
            } else if (content.getText() != null) {
                var sendMessage = new SendMessage(String.valueOf(content.getChatId()), content.getText());
                if (content.getMarkup() != null) {
                    sendMessage.setReplyMarkup(content.getMarkup());
                }
                execute(sendMessage);
            }
        } catch (TelegramApiException e) {
            throw new SentContentException(e.getMessage(), e);
        }
    }
}
