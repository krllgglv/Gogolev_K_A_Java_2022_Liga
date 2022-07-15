package ru.digitalleague.gogolev.commands.command;

import lombok.extern.slf4j.Slf4j;
import ru.digitalleague.gogolev.dto.InternalTaskDto;
import ru.digitalleague.gogolev.exceptions.InvalidCommandParametersException;
import ru.digitalleague.gogolev.services.TasksService;
import ru.digitalleague.gogolev.util.StringUtils;
import ru.digitalleague.gogolev.util.TaskUtils;

import java.util.Map;

@Slf4j
public class AlterTaskCommand extends AbstractParametrizedCommand<Long> {

    private final TasksService tasksService;
    public AlterTaskCommand(TasksService tasksService, Map<String, String> params) {
        super( params);
        this.tasksService = tasksService;
    }

    @Override
    public Long execute() {
        Long taskId = StringUtils.getIdFromString(parameters.get("i"));
        InternalTaskDto taskDto = new InternalTaskDto();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "i" -> taskDto.setId(taskId);
                case "t" -> taskDto.setTitle(value);
                case "d" -> taskDto.setDescription(value);
                case "u" -> taskDto.setUserId(StringUtils.getIdFromString(value));
                case "s" -> TaskUtils.setTaskStatus(taskDto, value);
                case "da" -> taskDto.setDateTo(StringUtils.validateAndParseDateFromString(value));
                default -> logParameterExc("No such parameter " + key);
            }
        }
        Long result = tasksService.update(taskDto);
        log.info("task altered, id=" + result);
        return result;
    }


    private void logParameterExc(String message) {
        log.error(message);
        throw new InvalidCommandParametersException(message);
    }

}
