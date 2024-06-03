package ru.tgbot.tgbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.tgbot.tgbot.model.JokeCall;
import ru.tgbot.tgbot.repository.JokeCallRepository;
import ru.tgbot.tgbot.repository.JokeRepository;
import ru.tgbot.tgbot.model.Joke;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


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
        Optional<Joke> jokeOptional = jokeRepository.findById(id);
        jokeOptional.ifPresent(joke -> {
            joke.setCalls(joke.getCalls() + 1);
            jokeRepository.save(joke);
        });
        return jokeOptional;
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

        joke.setCalls(joke.getCalls() + 1);
        jokeRepository.save(joke);

        JokeCall jokeCall = new JokeCall();
        jokeCall.setJoke(joke);
        jokeCall.setUserId(userId);
        jokeCall.setCallTime(LocalDateTime.now());
        jokeCallRepository.save(jokeCall);

        return jokeCallRepository.findByJokeId(id);
    }


    @Override
    public List<Joke> getTopJokes() {
        return jokeRepository.findTop5Jokes();
    }
    @Override
    public Joke getRandomJoke() {
        Joke randomJoke = jokeRepository.findRandomJoke();
        if (randomJoke != null) {
            randomJoke.setCalls(randomJoke.getCalls() + 1);
            jokeRepository.save(randomJoke);

            Long userId = generateUserId();

            JokeCall jokeCall = JokeCall.builder()
                    .joke(randomJoke)
                    .userId(userId)
                    .callTime(LocalDateTime.now())
                    .build();
            jokeCallRepository.save(jokeCall);
        }
        return randomJoke;
    }

    private Long generateUserId() {
        return ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
    }





}










