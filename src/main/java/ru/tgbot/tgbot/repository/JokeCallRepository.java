package ru.tgbot.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tgbot.tgbot.model.Joke;
import ru.tgbot.tgbot.model.JokeCall;

import java.util.List;
import java.util.Optional;

public interface JokeCallRepository extends JpaRepository<JokeCall, Long> {
    List<JokeCall> findByJokeId(Long id);
}
