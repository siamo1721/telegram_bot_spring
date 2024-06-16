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
        return jokeCallRepository.save(jokeCall);
    }

    @Override
    public Joke getJokeById(Long id) {
        return jokeRepository.findById(id).orElse(null);
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

    @Override
    public List<Joke> getTop5Jokes() {
        return jokeCallRepository.findTop5Jokes();
    }

    private Long generateUserId() {
        return ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
    }
}
