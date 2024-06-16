package ru.tgbot.tgbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tgbot.tgbot.model.Joke;
import ru.tgbot.tgbot.repository.JokeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JokeServiceImpl implements JokeService {
    private final JokeRepository jokeRepository;
    private final JokeCallService jokeCallService;

    @Override
    public Optional<Joke> addNewJoke(Joke newJoke) {
        try {
            newJoke.setTimeCreated(LocalDate.now());
            newJoke.setTimeUpdated(LocalDate.now());


            Joke savedJoke = jokeRepository.saveAndFlush(newJoke);
            return Optional.of(savedJoke);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Joke> getAllJokes() {
        return jokeRepository.findAll();
    }
    @Override
    public Joke getRandomJoke() {
        return jokeRepository.findRandomJoke();
    }


    @Override
    public Optional<Joke> getJokesById(Long id) {
        return Optional.ofNullable(jokeCallService.getJokeById(id));
    }

    @Override
    public Joke updateJoke(Long id, Joke joke) {
        if (jokeRepository.existsById(id)) {
            return jokeRepository.findById(id)
                    .map(existingJoke -> {
                        existingJoke.setJoke(joke.getJoke());
                        existingJoke.setTimeUpdated(LocalDate.now());
                        return jokeRepository.save(existingJoke);
                    })
                    .orElseThrow(() -> new IllegalArgumentException("Шутка с id=" + id + " не найдена"));
        } else {
            throw new IllegalArgumentException("Шутка с id=" + id + " не найдена");
        }
    }

    @Override
    public Joke deleteJoke(Joke deletedJoke) {
        return jokeRepository.findById(deletedJoke.getId())
                .map(existingJoke -> {
                    jokeRepository.deleteById(deletedJoke.getId());
                    return existingJoke;
                })
                .orElseThrow(() -> new IllegalArgumentException("Шутка с " + deletedJoke.getId() + " ID не найдена"));
    }


}
