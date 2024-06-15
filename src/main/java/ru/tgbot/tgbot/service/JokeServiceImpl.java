package ru.tgbot.tgbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.tgbot.tgbot.model.Joke;
import ru.tgbot.tgbot.model.JokeCall;
import ru.tgbot.tgbot.repository.JokeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class JokeServiceImpl implements JokeService {
    private final JokeRepository jokeRepository;
    private final JokeCallService jokeCallService;

    @Override
    public Optional<Joke> addNewJoke(@RequestBody Joke newJoke) {
        try {
            newJoke.setTimeCreated(LocalDate.now());
            newJoke.setTimeUpdated(LocalDate.now());
            newJoke.setCalls(0);

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
        return jokeRepository.findById(id);
    }

    @Override
    public Joke updateJoke(Long id, Joke joke) {
        if (jokeRepository.existsById(id)) {
            Optional<Joke> existingJokeOptional = jokeRepository.findById(id);
            if (existingJokeOptional.isPresent()) {
                Joke existingJoke = existingJokeOptional.get();

                existingJoke.setJoke(joke.getJoke());
                existingJoke.setTimeUpdated(LocalDate.now());

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
    public List<JokeCall> getJokeCallsByJokeId(Long id, Long userId) {
        Joke joke = jokeRepository.findById(id).orElse(null);
        if (joke == null) {
            return new ArrayList<>();
        }

        JokeCall jokeCall = JokeCall.builder()
                .joke(joke)
                .userId(userId)
                .callTime(LocalDateTime.now())
                .build();
        jokeCallService.createJokeCall(jokeCall);

        return jokeCallService.getJokeCallsByJokeId(id);
    }

    @Override
    public List<Joke> getTopJokes() {
        return jokeRepository.findTop5Jokes();
    }

    @Override
    public Joke getRandomJoke() {
        Joke randomJoke = jokeRepository.findRandomJoke();
        if (randomJoke != null) {
            Long userId = generateUserId();

            JokeCall jokeCall = JokeCall.builder()
                    .joke(randomJoke)
                    .userId(userId)
                    .callTime(LocalDateTime.now())
                    .build();
            jokeCallService.createJokeCall(jokeCall);
        }
        return randomJoke;
    }

    private Long generateUserId() {
        return ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
    }
}
