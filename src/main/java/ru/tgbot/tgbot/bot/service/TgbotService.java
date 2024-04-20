package ru.tgbot.tgbot.bot.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tgbot.tgbot.model.Joke;
import ru.tgbot.tgbot.repository.JokeRepository;


import java.util.List;
import java.util.Optional;
import java.util.Random;

@Data
@Component
@Service
public class TgbotService extends TelegramLongPollingBot {

    private final JokeRepository jokeRepository;

    @Value("${telegram.bot.name}")
    private String botName;

    @Value("${telegram.bot.token}")
    private String token;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String text = message.getText();
            long chatId = message.getChatId();

            switch (text) {
                case "/start":
                    startCommandReceived(chatId, message.getChat().getFirstName());
                    break;
                case "/joke":
                    sendRandomJoke(chatId);
                    break;
                case "/all_jokes":
                    sendAllJokes(chatId);
                    break;
                default:
                    if (text.startsWith("/")) {
                        sendMessage(chatId, "Такой команды не существует. Введите /joke для получения шутки");
                    }
            }
        }
    }

    private void startCommandReceived(long chatId, String name) {
        String answer = "Привет " + name + ", приятно познакомиться! \n" +
                "Доступные команды:\n" +
                "/joke - Получить случайную шутку\n" +
                "/all_jokes - Просмотреть все шутки";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendRandomJoke(long chatId) {
        List<Joke> jokes = jokeRepository.findAll();

        if (jokes.isEmpty()) {
            sendMessage(chatId, "No jokes available.");
        } else {
            Random random = new Random();
            int randomIndex = random.nextInt(jokes.size());
            Joke randomJoke = jokes.get(randomIndex);
            sendMessage(chatId, randomJoke.getJoke());
        }
    }

    private void sendAllJokes(long chatId) {
        List<Joke> jokes = jokeRepository.findAll();
        if (jokes.isEmpty()) {
            sendMessage(chatId, "No jokes available.");
        } else {
            StringBuilder allJokesText = new StringBuilder("All jokes:\n");
            for (Joke joke : jokes) {
                allJokesText.append(joke.getJoke()).append("\n");
            }
            sendMessage(chatId, allJokesText.toString());
        }
    }
}