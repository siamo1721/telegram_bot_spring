package ru.tgbot.tgbot.service;

import ru.tgbot.tgbot.model.JokeCall;

import java.util.List;

public interface JokeCallService {
    List<JokeCall> getJokeCallsByJokeId(Long jokeId);
    JokeCall createJokeCall(JokeCall jokeCall);
    void JokeCallCount(Long jokeId);
}
