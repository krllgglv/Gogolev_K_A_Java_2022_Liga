package ru.digitalleague.gogolev.entities.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.digitalleague.gogolev.entities.user.User;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor

public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private TaskStatus status;

    @Column(name = "date_to")
    private LocalDate dateTo;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

}
