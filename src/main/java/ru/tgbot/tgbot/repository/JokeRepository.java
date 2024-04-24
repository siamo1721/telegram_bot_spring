package ru.tgbot.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tgbot.tgbot.model.Joke;
import java.util.List;
import java.util.Optional;
@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {
    // Метод для получения шутки по её ID
    Optional<Joke> findById(Long id);
    List<Joke> findAll();

    // Метод для удаления шутки по её ID
    void deleteById(Long id);


}
