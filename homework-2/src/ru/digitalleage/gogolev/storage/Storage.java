package ru.digitalleage.gogolev.storage;

import ru.digitalleage.gogolev.entities.TaskStatus;
import ru.digitalleage.gogolev.entities.Task;
import ru.digitalleage.gogolev.entities.User;
import ru.digitalleage.gogolev.util.TimeUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Storage {

    private static final String PATH_TO_USER_FILE = "resources/users.csv";
    private static final String PATH_TO_TASK_FILE = "resources/tasks.csv";
    private final List<Task> tasks;
    private final List<User> users;

    public Storage() {
        this.tasks = initTasks();
        this.users = initUsers();
        setTasksForUser();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<User> getUsers() {
        return users;
    }
    public Optional<User> findUserById(Long id){
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
    }

    public Optional<Task> findTaskById(Long id){
        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }

    private List<User> initUsers() {
        List<User> users;
        try {
            return Files.lines(Path.of(PATH_TO_USER_FILE))
                    .map(line -> line.split(", "))
                    .map(this::createUserFromMetadata)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Task> initTasks() {
        try {
            return Files.lines(Path.of(PATH_TO_TASK_FILE))
                    .map(line -> line.split(", "))
                    .map(this::createTaskFromMetadata)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTasksForUser(){
        for (User user : users) {
            for (Task task : tasks) {
                if (task.getUserId() == user.getId()){
                    user.getTasks().add(task);
                }
            }
        }
    }

    private User createUserFromMetadata(String[] metadata) {
        var user = new User();
        user.setId(Long.parseLong(metadata[0]));
        user.setName(metadata[1]);
        return user;
    }

    private Task createTaskFromMetadata(String[] metadata) {
        var task = new Task();
        task.setId(Long.parseLong(metadata[0]));
        task.setTitle(metadata[1]);
        task.setDescription(metadata[2]);
        task.setUserId(Long.parseLong(metadata[3]));
        task.setToDate(TimeUtils.parseDateFromString(metadata[4]));
        task.setStatus(TaskStatus.NEW);
        return task;
    }
}
