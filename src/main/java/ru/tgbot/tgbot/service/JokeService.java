package ru.tgbot.tgbot.service;

import ru.tgbot.tgbot.model.Joke;
import ru.tgbot.tgbot.model.JokeCall;

import java.util.List;
import java.util.Optional;

public interface JokeService {
    List<Joke> getAllJokes();
    Joke updateJoke(Long id, Joke joke);

    Optional<Joke> addNewJoke(Joke json);
    Joke deleteJoke(Joke jokeToDelete );

    Optional<Joke> getJokesById(Long id);
    // В интерфейсе JokeService
    List<Joke> getTopJokes();
    List<JokeCall> getJokeCallsByJokeId(Long id, Long userId);


}