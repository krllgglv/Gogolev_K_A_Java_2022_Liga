package ru.digitalleague.homework5.gogolev.entities.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.digitalleague.homework5.gogolev.entities.task.Task;

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
