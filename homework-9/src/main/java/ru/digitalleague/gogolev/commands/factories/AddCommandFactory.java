package ru.digitalleague.gogolev.commands.factories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.digitalleague.gogolev.commands.command.Command;
import ru.digitalleague.gogolev.commands.command.AddTaskCommand;
import ru.digitalleague.gogolev.exceptions.InvalidCommandParameterValueException;
import ru.digitalleague.gogolev.exceptions.InvalidCommandParametersException;
import ru.digitalleague.gogolev.services.TasksService;
import ru.digitalleague.gogolev.util.StringUtils;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddCommandFactory implements ParametrizedCommandsAbstractFactory {
    private final TasksService tasksService;

    @Value("${check.pattern.addParams}")
    private String createCommandParamsPattern;

    @Override
    public Command<?> create(String... input) {
        if (input.length == 0) {
            logParameterExc("No parameters provided");
        }
        String lineWithParameters = input[0];
        Map<String, String> validatedParameters = validateParameters(lineWithParameters);
        return new AddTaskCommand(tasksService, validatedParameters);
    }

    @Override
    public Map<String, String> validateParameters(String input) {
        Map<String, String> params = StringUtils.getParametersFromRequest(input);
        if (params.isEmpty()) {
            logParameterExc("Wrong quantity of parameters, expected exactly 4");
        }
        if (params.size() != 4) {
            logParameterExc("Wrong quantity of parameters, expected exactly 4");
        }
        for (String param : params.keySet()) {
            if (!param.matches(createCommandParamsPattern)) {
                logParameterExc(String.format("Unknown parameter %s.", param));
            }
        }
        for (Map.Entry<String, String> entrySet : params.entrySet()) {
            String key = entrySet.getKey();

            switch (key) {
                case "u" -> {
                    if (!StringUtils.validateIdFromString(params.get(key))) {
                        logParameterValueExc("The value of parameter u should be positive digit");
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

    public void setCreateCommandParamsPattern(String createCommandParamsPattern) {
        this.createCommandParamsPattern = createCommandParamsPattern;
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
