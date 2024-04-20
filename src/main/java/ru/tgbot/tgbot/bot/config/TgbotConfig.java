package ru.tgbot.tgbot.bot.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.tgbot.tgbot.bot.service.TgbotService;

@Configuration
public class TgbotConfig {
    @Bean
    public CommandLineRunner registerTelegramBot(TgbotService tgbotService) {
        return args -> {
            try {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(tgbotService);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        };
    }
}
