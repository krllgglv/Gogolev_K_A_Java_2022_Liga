package ru.digitalleague.gogolev.commands.command;

import lombok.extern.slf4j.Slf4j;
import ru.digitalleague.gogolev.dto.InternalTaskDto;
import ru.digitalleague.gogolev.entities.task.TaskStatus;
import ru.digitalleague.gogolev.exceptions.InvalidCommandParametersException;
import ru.digitalleague.gogolev.services.TasksService;
import ru.digitalleague.gogolev.util.StringUtils;

import java.util.Map;

@Slf4j
public class AddTaskCommand extends AbstractParametrizedCommand<Long> {
    private final TasksService tasksService;

    public AddTaskCommand(TasksService tasksService, Map<String, String> params) {
        super(params);
        this.tasksService = tasksService;
    }

    @Override
    public Long execute() {
        InternalTaskDto taskDto = new InternalTaskDto();
        taskDto.setStatus(TaskStatus.NEW);

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "t" -> taskDto.setTitle(value);
                case "d" -> taskDto.setDescription(value);
                case "u" -> taskDto.setUserId(StringUtils.getIdFromString(value));
                case "da" -> taskDto.setDateTo(StringUtils.validateAndParseDateFromString(value));
                default -> logParameterExc("No such parameter " + key);
            }
        }
        tasksService.create(taskDto);
        log.info("task added, id=" + taskDto.getId());
        return taskDto.getId();
    }

    private void logParameterExc(String message) {
        log.error(message);
        throw new InvalidCommandParametersException(message);
    }

}
