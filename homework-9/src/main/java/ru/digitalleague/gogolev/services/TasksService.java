package ru.digitalleague.gogolev.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.digitalleague.gogolev.dto.InternalTaskDto;
import ru.digitalleague.gogolev.entities.task.Task;
import ru.digitalleague.gogolev.entities.user.User;
import ru.digitalleague.gogolev.exceptions.NoSuchTaskException;
import ru.digitalleague.gogolev.repository.TasksRepository;
import ru.digitalleague.gogolev.util.TaskUtils;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TasksService {
    private final TasksRepository tasksRepository;
    private final UsersService usersService;
    private final FileSystemProvider fileSystemProvider;
    @Value("${files.save.tasks}")
    private String pathToSaveTasksState;

    public Task findById(Long id) {
        Optional<Task> mayBeTask = tasksRepository.findById(id);
        if (mayBeTask.isEmpty()) {
            logNoSuchTaskExc("No task with id =" + id);
        }
        return mayBeTask.get();
    }


    public void clearState() {
        tasksRepository.deleteAll();
    }


    public void deleteTask(Long taskId) {
        tasksRepository.deleteById(taskId);
    }

    @Transactional
    public Long create(InternalTaskDto taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        User user = usersService.findById(taskDto.getUserId());
        task.setUser(user);
        task.setDateTo(taskDto.getDateTo());
        return tasksRepository.save(task).getId();
    }

    @Transactional
    public Long update(InternalTaskDto taskDto) {
        Optional<Task> mayBeTask = tasksRepository.findById(taskDto.getId());
        if (mayBeTask.isEmpty()) {
            logNoSuchTaskExc("No task with id =" + taskDto.getId());
        }
        Task task = mayBeTask.get();

        if (taskDto.getTitle() != null) {
            task.setTitle(taskDto.getTitle());
        }
        if (taskDto.getDescription() != null) {
            task.setDescription(taskDto.getDescription());
        }
        if (taskDto.getStatus() != null) {
            task.setStatus(taskDto.getStatus());
        }
        if (taskDto.getUserId() != null) {
            User user = usersService.findById(taskDto.getUserId());
            task.setUser(user);
        }

        if (taskDto.getDateTo() != null) {
            task.setDateTo(taskDto.getDateTo());
        }
        return tasksRepository.save(task).getId();
    }

    public List<Task> findAll(){
        return tasksRepository.findAll();
    }

    public void saveStateToFile() {
        List<Task> tasks = findAll();
        List<String> content = tasks.stream()
                .sorted(Comparator.comparingLong(Task::getId))
                .map(TaskUtils::convertTaskToCSVString)
                .toList();
        fileSystemProvider.saveStateToFile(pathToSaveTasksState, content);
    }

    private void logNoSuchTaskExc(String message) {
        log.error(message);
        throw new NoSuchTaskException(message);
    }
}
