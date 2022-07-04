package ru.digitalleague.homework4.gogolev.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.digitalleague.homework4.gogolev.dto.InternalTaskDto;
import ru.digitalleague.homework4.gogolev.entities.task.Task;
import ru.digitalleague.homework4.gogolev.exceptions.NoSuchTaskException;
import ru.digitalleague.homework4.gogolev.repository.TasksRepository;
import ru.digitalleague.homework4.gogolev.util.TaskUtils;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class TasksService {

    private final TasksRepository tasksRepository;

    public Set<InternalTaskDto> findAll(){
        return tasksRepository.findAll().stream().map(TaskUtils::entityToInternalDto).collect(Collectors.toSet());
    }

    public InternalTaskDto findById(Long id){
        Optional<Task> mayBeTask = tasksRepository.findTaskById(id);
        if (mayBeTask.isEmpty()){
            log.error("No task with id =" + id);
            throw new NoSuchTaskException("No task with id =" + id);
        }
        return TaskUtils.entityToInternalDto(mayBeTask.get());
    }


    public void clearState() {
        tasksRepository.clearState();
    }

    public void saveStateToFile(){
        tasksRepository.saveStateToFile();
    }
    public Long generateTaskId() {
        return tasksRepository.generateTaskId();
    }

    public void deleteTask(Long taskId){
        tasksRepository.deleteTask(taskId);
    }

    public Long save(InternalTaskDto taskDto) {
        Task task = TaskUtils.internalDtoToEntity(taskDto);
        return tasksRepository.saveOrUpdateTask(task);
    }
}
