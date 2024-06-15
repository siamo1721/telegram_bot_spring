package ru.tgbot.tgbot.service;

import ru.tgbot.tgbot.model.Joke;
import ru.tgbot.tgbot.model.JokeCall;

import java.util.List;

public interface JokeCallService {
    List<JokeCall> getJokeCallsByJokeId(Long jokeId);
    JokeCall createJokeCall(JokeCall jokeCall);
    void JokeCallCount(Long jokeId);
    Joke getJokeById(Long id);
    Joke getRandomJoke();
    List<JokeCall> JokeCallsByJokeId(Long jokeId, Long userId);
}
