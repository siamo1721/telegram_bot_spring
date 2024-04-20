package ru.tgbot.tgbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.tgbot.tgbot.model.JokeCall;
import ru.tgbot.tgbot.model.JokeHistory;
import ru.tgbot.tgbot.repository.JokeCallRepository;
import ru.tgbot.tgbot.repository.JokeRepository;
import ru.tgbot.tgbot.model.Joke;

import java.time.LocalDate;
import java.util.*;


@Service
@RequiredArgsConstructor
public class JokeServiceImpl implements JokeService {
    private final JokeRepository jokeRepository;
    private final JokeCallRepository jokeCallRepository;


    @Override
    public Optional<Joke> addNewJoke(@RequestBody Joke newJoke) {
        try {
            newJoke.setTimeCreated(LocalDate.now());
            newJoke.setTimeUpdated(LocalDate.now());
            if (newJoke.getJokeHistory() == null) {
                newJoke.setJokeHistory(new ArrayList<>());
            }
            newJoke.getJokeHistory().add(new JokeHistory(null, newJoke, new Date()));

            Joke savedJoke = jokeRepository.saveAndFlush(newJoke);

            return Optional.of(savedJoke);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }


    }

    @Override
    public List<Joke> getAllJokes() {
        return jokeRepository.findAll();
    }

    @Override
    public Optional<Joke> getJokesById(Long id) {
        List<Joke> allJokes = getAllJokes();
        return allJokes.stream()
                .filter(joke -> joke.getId().equals(id))
                .findFirst();
    }

    @Override
    public Joke updateJoke(Long id, Joke joke) {
        // Проверяем, существует ли шутка с данным id
        if (jokeRepository.existsById(id)) {
            // Получаем текущую шутку по id
            Optional<Joke> existingJokeOptional = jokeRepository.findById(id);
            if (existingJokeOptional.isPresent()) {
                Joke existingJoke = existingJokeOptional.get();

                // Обновляем данные шутки
                existingJoke.setJoke(joke.getJoke());
                existingJoke.setTimeUpdated(LocalDate.now()); // Обновляем дату обновления

                // Сохраняем обновленную шутку

                return jokeRepository.save(existingJoke);
            } else {
                throw new IllegalArgumentException("Шутка с id=" + id + " не найдена");
            }
        } else {
            throw new IllegalArgumentException("Шутка с id=" + id + " не найдена");
        }
    }
    @Override
    public Joke deleteJoke(Joke deletedJoke) {
        Optional<Joke> existingJoke = jokeRepository.findById(deletedJoke.getId());
        if (existingJoke.isPresent()) {
            jokeRepository.deleteById(deletedJoke.getId());
            return existingJoke.get();
        } else {
            throw new NoSuchElementException("Шутка с " + deletedJoke.getId() + " ID не найдена");
        }
    }
    @Override
    public List<JokeCall> getJokeCallsByJokeId(Long id) {
        return jokeCallRepository.findByJokeId(id);
    }

}










