package ru.tgbot.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tgbot.tgbot.model.JokeCall;

import java.util.List;
@Repository
public interface JokeCallRepository extends JpaRepository<JokeCall, Long> {
    List<JokeCall> findByJokeIdAndUserId(Long jokeId, Long userId);
    List<JokeCall> findByJokeId(Long jokeId);
}
