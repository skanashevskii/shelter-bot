package ru.devpro.listener;

import com.pengrad.telegrambot.model.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface Command {
    void handle(Update update) throws TelegramApiException;

    boolean ifSuitable(Update update);


}
