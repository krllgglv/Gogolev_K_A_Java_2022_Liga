package ru.digitalleague.gogolev.entities.user;

import lombok.*;
import ru.digitalleague.gogolev.entities.task.Task;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class User {
    private Long id;
    private String name;
    private List<Task> tasks;


    public User() {
        this.tasks = new ArrayList<>();
    }
}
