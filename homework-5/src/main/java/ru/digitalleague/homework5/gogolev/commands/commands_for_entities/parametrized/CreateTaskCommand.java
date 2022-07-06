package ru.digitalleague.homework5.gogolev.commands.commands_for_entities.parametrized;

import lombok.extern.slf4j.Slf4j;
import ru.digitalleague.homework5.gogolev.commands.AbstractParametrizedEntityCommand;
import ru.digitalleague.homework5.gogolev.dto.InternalTaskDto;
import ru.digitalleague.homework5.gogolev.entities.task.TaskStatus;
import ru.digitalleague.homework5.gogolev.exceptions.InvalidCommandParameterValueException;
import ru.digitalleague.homework5.gogolev.exceptions.InvalidCommandParametersException;
import ru.digitalleague.homework5.gogolev.services.TasksService;
import ru.digitalleague.homework5.gogolev.services.UsersService;
import ru.digitalleague.homework5.gogolev.util.StringUtils;

import java.util.Map;

@Slf4j
public class CreateTaskCommand extends AbstractParametrizedEntityCommand<Long> {
    public CreateTaskCommand(UsersService usersService, TasksService tasksService, String input) {
        super(usersService, tasksService, input);
    }

    private static final String CREATE_COMMAND_PARAMS_PATTERN = "t|d|u|da";


    @Override
    protected Map<String, String> validateAndGetParameters(String input) {
        Map<String, String> params = StringUtils.getParametersFromRequest(input);
        return validateParameters(params);
    }

    @Override
    public Long execute() {
        InternalTaskDto taskDto = new InternalTaskDto();
        Long id = tasksService.generateTaskId();
        taskDto.setId(id);
        taskDto.setStatus(TaskStatus.NEW);

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "t" -> taskDto.setTitle(value);
                case "d" -> taskDto.setDescription(value);
                case "u" -> taskDto.setUserId(StringUtils.getIdFromString(value));
                case "da" -> taskDto.setToDate(StringUtils.validateAndParseDateFromString(value));
                default -> logParameterExc("No such parameter " + key);
            }
        }
        tasksService.save(taskDto);
        usersService.setTasksForUsers();
        log.info("task added, id=" + taskDto.getId());
        return taskDto.getId();
    }

    private Map<String, String> validateParameters(Map<String, String> params) {
        if (params.isEmpty()) {
            logParameterExc("no parameters provided, expected exactly 4");
        }
        if (params.size() != 4) {
            logParameterExc("Wrong quantity of parameters, expected exactly 4");
        }

        for (String param : params.keySet()) {
            if (!param.matches(CREATE_COMMAND_PARAMS_PATTERN)) {
                logParameterExc(String.format("Unknown parameter %s.", param));
            }
        }
        for (Map.Entry<String, String> entrySet : params.entrySet()) {
            String key = entrySet.getKey();

            switch (key) {
                case "u" -> {
                    if (!StringUtils.validateIdFromString(params.get(key))) {
                        logParameterValueExc("The value of parameter i should be digit");
                    }
                }
                case "t" -> {
                    if (params.get(key).isEmpty()) {
                        logParameterValueExc("The value of parameter t should not  be empty");
                    }
                }

                case "d" -> {
                    if (params.get(key).isEmpty()) {
                        logParameterValueExc("The value of parameter d should not be empty");
                    }
                }
                case "da" -> StringUtils.validateAndParseDateFromString(params.get(key));
                default -> logParameterExc("Unknown parameter" + key);
            }
        }
        return params;
    }

    private void logParameterExc(String message) {
        log.error(message);
        throw new InvalidCommandParametersException(message);
    }

    private void logParameterValueExc(String message) {
        log.error(message);
        throw new InvalidCommandParameterValueException(message);
    }


}
