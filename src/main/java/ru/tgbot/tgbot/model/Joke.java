package ru.tgbot.tgbot.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "jokes")
@Table(name = "jokes")
public class Joke {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jokes_seq")
    @SequenceGenerator(name = "jokes_seq", sequenceName = "jokes_sequence", initialValue = 1 ,allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "joke")
    private String joke;

    @Column(name = "calls")
    private int calls;

    @Column(name = "timeCreated")
    private LocalDate timeCreated;

    @Column(name = "timeUpdated")
    private LocalDate timeUpdated;



}