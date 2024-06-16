package ru.tgbot.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tgbot.tgbot.model.Joke;
import ru.tgbot.tgbot.model.JokeCall;

import java.util.List;

@Repository
public interface JokeCallRepository extends JpaRepository<JokeCall, Long> {

    List<JokeCall> findByJokeId(Long jokeId);

    @Query(value = "SELECT j.* FROM jokes j " +
            "JOIN joke_calls jc ON j.id = jc.joke_id " +
            "GROUP BY j.id " +
            "ORDER BY COUNT(jc.id) DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Joke> findTop5Jokes();


}
