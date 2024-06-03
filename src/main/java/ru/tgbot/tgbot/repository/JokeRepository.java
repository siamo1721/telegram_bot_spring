package ru.tgbot.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tgbot.tgbot.model.Joke;
import java.util.List;
import java.util.Optional;
@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {
    Optional<Joke> findById(Long id);
    List<Joke> findAll();

    void deleteById(Long id);
    @Query(value = "SELECT * FROM jokes ORDER BY calls DESC LIMIT 5", nativeQuery = true)
    List<Joke> findTop5Jokes();
    @Query(value = "SELECT * FROM jokes ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Joke findRandomJoke();
}
