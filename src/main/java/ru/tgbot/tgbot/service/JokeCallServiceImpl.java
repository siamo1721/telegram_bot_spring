package ru.tgbot.tgbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tgbot.tgbot.model.JokeCall;
import ru.tgbot.tgbot.repository.JokeCallRepository;
import ru.tgbot.tgbot.repository.JokeRepository;

import java.util.List;

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
}
