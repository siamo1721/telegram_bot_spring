package ru.tgbot.tgbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@EqualsAndHashCode
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "jokes_history")
public class JokeHistory {
    @Id
    @GeneratedValue(generator = "jokes_history_seq",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "jokes_history_seq", sequenceName = "jokes_sequence",initialValue = 1 ,allocationSize = 1)
    @Column(name ="id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "joke_id")
    private Joke joke;

    @Column(name="Date")
    private Date date;
}
