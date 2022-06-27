package ru.digitalleage.gogolev.repository;

import ru.digitalleage.gogolev.entities.Task;
import ru.digitalleage.gogolev.util.TaskUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.Collectors;

public class TasksRepository {

    private static final String PATH_TO_TASK_FILE = "resources/tasks.csv";
    private static final String SAVED_TASK_STATE_FILE = "resources/tasks_saved.csv";
    private final Set<Task> tasks;

    public TasksRepository() {
        this.tasks = initTasks();
    }

    public Set<Task> getTasks() {
        return tasks;
    }


    public Optional<Task> findTaskById(Long id) {
        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }


    private Set<Task> initTasks() {
        Path migrationPath = Path.of(PATH_TO_TASK_FILE);
        Path savedFilePath = Path.of(SAVED_TASK_STATE_FILE);
        Path path;
        if (savedFilePath.toFile().exists()) {
            path = savedFilePath;
        } else {
            path = migrationPath;
        }
        try {
            return Files.lines(path)
                    .map(line -> line.split(", "))
                    .map(TaskUtils::createTaskFromMetadata)
                    .collect(Collectors.toSet());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTask(Long taskId) {
        Optional<Task> taskById = findTaskById(taskId);
        taskById.ifPresentOrElse(task -> {
                    tasks.remove(task);
                    System.out.println(String.format(" Задача с id=%d успешно удалена", taskId));
                },
                () -> System.out.println("Отсутстует задача с id =" + taskId));
    }


    public Long generateTaskId() {
        OptionalLong max = tasks.stream()
                .mapToLong(Task::getId)
                .max();
        return max.orElse(0L) + 1L;
    }

    public void saveTask(Task task) {
        tasks.add(task);
    }

    public void clearState() {
        tasks.clear();
    }

    public void saveStateToFile() {
        try {
            Path path = Path.of(SAVED_TASK_STATE_FILE);
            File file = path.toFile();
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            for (Task task : tasks) {

                Files.writeString(path, TaskUtils.convertTaskToCSVString(task), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


