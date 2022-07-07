package ru.digitalleague.homework5.gogolev.repository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.digitalleague.homework5.gogolev.entities.task.Task;
import ru.digitalleague.homework5.gogolev.exceptions.CantReadFileException;
import ru.digitalleague.homework5.gogolev.exceptions.CantSaveFileException;
import ru.digitalleague.homework5.gogolev.exceptions.NoSuchTaskException;
import ru.digitalleague.homework5.gogolev.util.TaskUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@NoArgsConstructor
@Slf4j
public class TasksRepository {

    @Value("${files.init.tasks}")
    private String pathToInitTaskFile;
    @Value("${files.save.tasks}")
    private String pathToSaveTasksState;
    private Set<Task> tasks;


    public Set<Task> findAll() {
        return tasks;
    }


    public Optional<Task> findTaskById(Long id) {
        return tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    @PostConstruct
    private void initTasks() {
        this.tasks = new HashSet<>();
        Path migrationPath = Path.of(pathToInitTaskFile);
        Path savedFilePath = Path.of(pathToSaveTasksState);
        Path path;
        if (savedFilePath.toFile().exists()) {
            path = savedFilePath;
        } else {
            path = migrationPath;
        }
        try (Stream<String> stream = Files.lines(path)) {
            tasks.addAll(stream
                    .map(line -> line.split(", "))
                    .map(TaskUtils::createTaskFromMetadata)
                    .collect(Collectors.toSet()));

        } catch (IOException e) {
            log.error("Cant read file to initialize tasks!", e);
            throw new CantReadFileException("Cant read file to initialize tasks!", e);
        }
    }

    public Boolean deleteTask(Long taskId) {
        Optional<Task> task = findTaskById(taskId);
        if (task.isPresent()) {
            return tasks.remove(task.get());
        } else {
            log.error(String.format("Cant delete. Task with id = %s is absent", taskId));
            throw new NoSuchTaskException(String.format("Cant delete. Task with id = %s is absent", taskId));
        }
    }

    public Long saveOrUpdateTask(Task task) {
        if (!tasks.add(task)) {
            tasks.remove(task);
            tasks.add(task);
        }
        return task.getId();
    }

    public Long generateTaskId() {
        OptionalLong max = tasks.stream()
                .mapToLong(Task::getId)
                .max();
        return max.orElse(0L) + 1L;
    }

    public void clearState() {
        tasks.clear();
    }

    public void saveStateToFile() {
        try {
            Path path = Path.of(pathToSaveTasksState);
            File file = path.toFile();
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            for (Task task : tasks) {
                Files.writeString(path, TaskUtils.convertTaskToCSVString(task), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            log.error("Cant save state of tasks to file", e);
            throw new CantSaveFileException("Cant save state of tasks to file", e);
        }
    }
}


