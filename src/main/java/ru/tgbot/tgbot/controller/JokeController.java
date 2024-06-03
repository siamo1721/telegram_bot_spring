package ru.tgbot.tgbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tgbot.tgbot.model.Joke;
import ru.tgbot.tgbot.model.JokeCall;
import ru.tgbot.tgbot.service.JokeService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("Jokes")
@RequiredArgsConstructor
public class JokeController {
    private final JokeService jokeService;

    @GetMapping
    public ResponseEntity<List<Joke>> getAllJokes() {
        List<Joke> jokes = jokeService.getAllJokes();
        return ResponseEntity.ok(jokes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Joke> getJokeById(@PathVariable("id") Long id) {
        Optional<Joke> joke = jokeService.getJokesById(id);
        return joke.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Joke> addNewJoke(@RequestBody Joke newJoke) {
        Optional<Joke> savedJoke = jokeService.addNewJoke(newJoke);
        return savedJoke.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Joke> updateJoke(@PathVariable Long id, @RequestBody Joke updatedJoke) {
        Joke updatedJokes = jokeService.updateJoke(id, updatedJoke);
        return ResponseEntity.ok(updatedJokes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Joke> deleteJoke(@PathVariable Long id) {
        Optional<Joke> deleteToJoke = jokeService.getJokesById(id);
        if (deleteToJoke.isPresent()) {
            Joke jokeToDelete = deleteToJoke.get();
            Joke deleteJoke = jokeService.deleteJoke(jokeToDelete);
            return ResponseEntity.ok(deleteJoke);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/top")
    public ResponseEntity<List<Joke>> getTopJokes() {
        List<Joke> topJokes = jokeService.getTopJokes();
        return ResponseEntity.ok(topJokes);
    }

    @GetMapping("/random")
    public ResponseEntity<Joke> getRandomJoke() {
        Joke randomJoke = jokeService.getRandomJoke();
        return ResponseEntity.ok(randomJoke);
    }
    @GetMapping("/calls/{id}")
    public ResponseEntity<List<JokeCall>> getJokeCallsByJokeId(@PathVariable("id") Long id) {
        Long userId = generateUserId();
        List<JokeCall> jokeCalls = jokeService.getJokeCallsByJokeId(id, userId);
        return ResponseEntity.ok(jokeCalls);
    }

    private Long generateUserId() {
        return ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
    }
}


