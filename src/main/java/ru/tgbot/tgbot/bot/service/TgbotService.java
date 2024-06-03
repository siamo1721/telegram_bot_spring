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
import ru.tgbot.tgbot.service.JokeService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Data
@Component
@Service
public class TgbotService extends TelegramLongPollingBot {
    private final JokeService jokeService;

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
                case "/top5jokes":
                    sendTop5Jokes(chatId);
                    break;
                case "/add_joke":
                    sendMessage(chatId, "Введите новую шутку:");
                    break;
                default:
                    if (text.startsWith("/")) {
                        sendMessage(chatId, "Такой команды не существует. Введите /joke для получения шутки");
                    } else {
                        saveJoke(chatId, text);
                    }
            }
        }
    }

    private void startCommandReceived(long chatId, String name) {
        String answer = "Привет " + name + ", приятно познакомиться! \n" +
                "Доступные команды:\n" +
                "/joke - Получить случайную шутку\n" +
                "/all_jokes - Просмотреть все шутки\n" +
                "/top5jokes - топ-5 популярных шуток\n" +
                "/add_joke - добавление новой шутки";
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

    public void sendTop5Jokes(long chatId) {
        List<Joke> top5Jokes = jokeService.getTopJokes();

        if (top5Jokes.isEmpty()) {
            sendMessage(chatId, "No jokes available.");
        } else {
            StringBuilder top5JokesText = new StringBuilder("Top 5 jokes:\n");
            for (int i = 0; i < Math.min(5, top5Jokes.size()); i++) {
                top5JokesText.append(i + 1).append(". ").append(top5Jokes.get(i).getJoke()).append("\n");
            }
            sendMessage(chatId, top5JokesText.toString());
        }
    }

    private void sendRandomJoke(long chatId) {
        Joke randomJoke = jokeService.getRandomJoke();

        if (randomJoke == null) {
            sendMessage(chatId, "No jokes available.");
        } else {
            sendMessage(chatId, randomJoke.getJoke());
        }
    }

    private void sendAllJokes(long chatId) {
        List<Joke> jokes = jokeService.getAllJokes();
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

    private void saveJoke(long chatId, String jokeText) {
        Joke newJoke = new Joke();
        newJoke.setJoke(jokeText);
        newJoke.setTimeCreated(LocalDate.now());
        newJoke.setTimeUpdated(LocalDate.now());
        newJoke.setCalls(0);
        jokeService.addNewJoke(newJoke);
        sendMessage(chatId, "Шутка успешно добавлена!");
    }
}
