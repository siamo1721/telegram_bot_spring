package ru.tgbot.tgbot.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "jokes") //Объявляем класс как сущность для работы с ним в БД и его имя
@Table(name = "jokes") //Помечаем, как называется таблица в БД
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

    @OneToMany(mappedBy = "joke",cascade = CascadeType.ALL)
    private List<JokeHistory> jokeHistory;

}