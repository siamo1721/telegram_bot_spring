package ru.tgbot.tgbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tgbot.tgbot.model.Joke;
import ru.tgbot.tgbot.model.JokeCall;
import ru.tgbot.tgbot.service.JokeCallService;
import ru.tgbot.tgbot.service.JokeService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("Jokes")
@RequiredArgsConstructor
public class JokeController {
    private final JokeCallService jokeCallService;
    private final JokeService jokeService;

    @GetMapping
    public ResponseEntity<List<Joke>> getAllJokes() {
        return ResponseEntity.ok(jokeService.getAllJokes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Joke> getJokeById(@PathVariable("id") Long id) {
        Joke joke = jokeCallService.getJokeById(id);
        return joke != null ? ResponseEntity.ok(joke) : ResponseEntity.notFound().build();
    }

    @GetMapping("/top")
    public ResponseEntity<List<Joke>> getTopJokes() {
        List<Joke> topJokes = jokeCallService.getTop5Jokes();
        return ResponseEntity.ok(topJokes);
    }

    @GetMapping("/random")
    public ResponseEntity<Joke> getRandomJoke() {
        Joke randomJoke = jokeService.getRandomJoke();
        return ResponseEntity.ok(randomJoke);
    }
    @PostMapping
    public ResponseEntity<Joke> addNewJoke(@RequestBody Joke newJoke) {
        Optional<Joke> savedJoke = jokeService.addNewJoke(newJoke);
        return savedJoke.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    @GetMapping("/calls/{id}")
    public ResponseEntity<List<JokeCall>> getJokeCallsByJokeId(@PathVariable("id") Long id) {
        Long userId = generateUserId();
        List<JokeCall> jokeCalls = jokeCallService.JokeCallsByJokeId(id, userId);
        return ResponseEntity.ok(jokeCalls);
    }

    private Long generateUserId() {
        return ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
    }
}
