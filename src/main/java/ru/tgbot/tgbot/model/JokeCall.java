package ru.tgbot.tgbot.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "joke_calls")
@Table(name = "joke_calls")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class JokeCall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "joke_id")
    private Joke joke;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "call_time")
    private LocalDateTime callTime;
}

