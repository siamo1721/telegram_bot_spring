package ru.tgbot.tgbot.service;

import ru.tgbot.tgbot.model.Joke;

import java.util.List;
import java.util.Optional;

public interface JokeService {
    List<Joke> getAllJokes();
    Joke updateJoke(Long id, Joke joke);
    Optional<Joke> addNewJoke(Joke json);
    Joke deleteJoke(Joke jokeToDelete );

    Joke getRandomJoke();

    Optional<Joke> getJokesById(Long id);
}