package ru.devpro.config;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration

public class AppConfig {
    @Value("${telegram-bot.token}")
    private String token;

    @Bean
    public TelegramBot bot() {
        var bot = new TelegramBot(token);
        bot.execute(new DeleteMyCommands());
        return bot;
    }
}