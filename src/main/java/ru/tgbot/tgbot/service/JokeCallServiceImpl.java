package ru.tgbot.tgbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tgbot.tgbot.model.Joke;
import ru.tgbot.tgbot.model.JokeCall;
import ru.tgbot.tgbot.repository.JokeCallRepository;
import ru.tgbot.tgbot.repository.JokeRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class JokeCallServiceImpl implements JokeCallService {
    private final JokeCallRepository jokeCallRepository;
    private final JokeRepository jokeRepository;

    @Override
    public List<JokeCall> getJokeCallsByJokeId(Long jokeId) {
        return jokeCallRepository.findByJokeId(jokeId);
    }

    @Override
    public JokeCall createJokeCall(JokeCall jokeCall) {
        JokeCall savedJokeCall = jokeCallRepository.save(jokeCall);
        JokeCallCount(jokeCall.getJoke().getId());
        return savedJokeCall;
    }

    @Override
    public void JokeCallCount(Long jokeId) {
        jokeRepository.findById(jokeId).ifPresent(joke -> {
            joke.setCalls(joke.getCalls() + 1);
            jokeRepository.save(joke);
        });
    }

    @Override
    public Joke getJokeById(Long id) {
        Optional<Joke> joke = jokeRepository.findById(id);
        joke.ifPresent(j -> {
            j.setCalls(j.getCalls() + 1);
            jokeRepository.save(j);
        });
        return joke.orElse(null);
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

    @Override
    public List<JokeCall> JokeCallsByJokeId(Long jokeId, Long userId) {
        Joke joke = getJokeById(jokeId);
        if (joke == null) {
            return new ArrayList<>();
        }

        JokeCall jokeCall = JokeCall.builder()
                .joke(joke)
                .userId(userId)
                .callTime(LocalDateTime.now())
                .build();
        createJokeCall(jokeCall);

        return getJokeCallsByJokeId(jokeId);
    }

    private Long generateUserId() {
        return ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
    }
}
