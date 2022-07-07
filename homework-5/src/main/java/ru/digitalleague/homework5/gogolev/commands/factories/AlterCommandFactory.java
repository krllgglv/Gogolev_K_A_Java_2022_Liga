package ru.digitalleague.homework5.gogolev.commands.factories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.digitalleague.homework5.gogolev.commands.Command;
import ru.digitalleague.homework5.gogolev.commands.commands_for_entities.parametrized.AddTaskCommand;
import ru.digitalleague.homework5.gogolev.commands.commands_for_entities.parametrized.AlterTaskCommand;
import ru.digitalleague.homework5.gogolev.exceptions.InvalidCommandParameterValueException;
import ru.digitalleague.homework5.gogolev.exceptions.InvalidCommandParametersException;
import ru.digitalleague.homework5.gogolev.services.TasksService;
import ru.digitalleague.homework5.gogolev.services.UsersService;
import ru.digitalleague.homework5.gogolev.util.StringUtils;
import ru.digitalleague.homework5.gogolev.util.TaskUtils;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlterCommandFactory implements ParametrizedCommandsAbstractFactory{

    private final UsersService usersService;
    private final TasksService tasksService;
    @Value("${check.pattern.alterParams}")
    private String alterCommandParamsPattern;

    @Override
    public Command<?> create(String ... input) {
        if (input.length == 0) {
            logParameterExc("No parameters provided");
        }
        String lineWithParameters = input[0];
        Map<String, String> validatedParameters = validateParameters(lineWithParameters);
        return new AlterTaskCommand(usersService, tasksService, validatedParameters);
    }

    @Override
    public Map<String, String> validateParameters(String input) {
        Map<String, String> params = StringUtils.getParametersFromRequest(input);
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
            if (!param.matches(alterCommandParamsPattern)) {
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
