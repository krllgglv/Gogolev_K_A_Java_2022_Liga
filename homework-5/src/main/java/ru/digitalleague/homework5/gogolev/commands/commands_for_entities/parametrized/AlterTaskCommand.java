package ru.digitalleague.homework5.gogolev.commands.commands_for_entities.parametrized;

import lombok.extern.slf4j.Slf4j;
import ru.digitalleague.homework5.gogolev.commands.AbstractParametrizedEntityCommand;
import ru.digitalleague.homework5.gogolev.dto.InternalTaskDto;
import ru.digitalleague.homework5.gogolev.exceptions.InvalidCommandParameterValueException;
import ru.digitalleague.homework5.gogolev.exceptions.InvalidCommandParametersException;
import ru.digitalleague.homework5.gogolev.services.TasksService;
import ru.digitalleague.homework5.gogolev.services.UsersService;
import ru.digitalleague.homework5.gogolev.util.StringUtils;
import ru.digitalleague.homework5.gogolev.util.TaskUtils;

import java.util.Map;

@Slf4j
public class AlterTaskCommand extends AbstractParametrizedEntityCommand<Long> {
    private static final String ALTER_COMMAND_PARAMS_PATTERN = "i|t|d|u|s|da";

    public AlterTaskCommand(UsersService usersService, TasksService tasksService, String input) {
        super(usersService, tasksService, input);
    }

    @Override
    protected Map<String, String> validateAndGetParameters(String input) {
        Map<String, String> params = StringUtils.getParametersFromRequest(input);
        return validateParameters(params);


    }

    @Override
    public Long execute() {
        Long taskId = StringUtils.getIdFromString(parameters.get("i"));
        InternalTaskDto taskDto = tasksService.findById(taskId);
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "i" -> {}
                case "t" -> taskDto.setTitle(value);
                case "d" -> taskDto.setDescription(value);
                case "u" -> taskDto.setUserId(StringUtils.getIdFromString(value));
                case "s" -> TaskUtils.setTaskStatus(taskDto, value);
                case "da" -> taskDto.setToDate(StringUtils.validateAndParseDateFromString(value));
                default -> logParameterExc("No such parameter " + key);
            }
        }
        Long result = tasksService.save(taskDto);
        usersService.setTasksForUsers();
        return result;
    }

    private Map<String, String> validateParameters(Map<String, String> params) {
        if (params.isEmpty()) {
            logParameterExc("no parameters provided, expected at least 1");
        }
        if (params.size() > 6) {
            logParameterExc("To many parameters, expected max 6");
        }
        if (!params.containsKey("i")) {
            logParameterExc("!!Id of task is absent!!");
        }

        for (String param : params.keySet()) {
            if (!param.matches(ALTER_COMMAND_PARAMS_PATTERN)) {
                logParameterExc(String.format("Unknown parameter %s.", param));
            }
        }
        for (Map.Entry<String, String> entrySet : params.entrySet()) {
            String key = entrySet.getKey();

            switch (key) {
                case "i", "u" -> {
                    if (!StringUtils.validateIdFromString(params.get(key))) {
                        logParameterValueExc("The value of parameter  should be digit");
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
                case "s" -> {
                    if (!TaskUtils.checkNewTaskStatus(params.get(key))) {
                        logParameterValueExc("Unknown value for status parameter");
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
