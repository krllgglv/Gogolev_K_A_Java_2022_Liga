package ru.digitalleage.gogolev.entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;
    private String name;
    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    public User() {
        this.tasks = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
