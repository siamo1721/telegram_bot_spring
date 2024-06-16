package ru.tgbot.tgbot.service;

import ru.tgbot.tgbot.model.Joke;
import ru.tgbot.tgbot.model.JokeCall;

import java.util.List;

public interface JokeCallService {
    List<JokeCall> getJokeCallsByJokeId(Long jokeId);
    JokeCall createJokeCall(JokeCall jokeCall);
    Joke getJokeById(Long id);
    List<JokeCall> JokeCallsByJokeId(Long jokeId, Long userId);
    List<Joke> getTop5Jokes();
}
