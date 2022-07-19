package ru.digitalleague.gogolev.commands.factories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.digitalleague.gogolev.commands.command.Command;
import ru.digitalleague.gogolev.commands.command.FindBusiestUserCommand;
import ru.digitalleague.gogolev.exceptions.InvalidCommandParameterValueException;
import ru.digitalleague.gogolev.exceptions.InvalidCommandParametersException;
import ru.digitalleague.gogolev.services.UsersService;
import ru.digitalleague.gogolev.util.StringUtils;
import ru.digitalleague.gogolev.util.TaskUtils;

import java.util.Collections;
import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class FindBusiestUserCommandFactory implements ParametrizedCommandsAbstractFactory{

    private final UsersService usersService;
    @Value("${check.pattern.findBusiestParams}")
    private String findBusiestCommandParamsPattern;


    @Override
    public Command<?> create(String... input) {
        if (input.length == 0) {
            return new FindBusiestUserCommand(usersService, Collections.emptyMap());
        }
        String lineWithParameters = input[0];
        Map<String, String> validatedParameters = validateParameters(lineWithParameters);
        return new FindBusiestUserCommand(usersService, validatedParameters);
    }

    @Override
    public Map<String, String> validateParameters(String input) {
        Map<String, String> params = StringUtils.getParametersFromRequest(input);

        if (params.size() > 3) {
            logParameterExc("Wrong quantity of parameters, expected max 3");
        }
        for (String param : params.keySet()) {
            if (!param.matches(findBusiestCommandParamsPattern)) {
                logParameterExc(String.format("Unknown parameter %s.", param));
            }
        }
        for (Map.Entry<String, String> entrySet : params.entrySet()) {
            String key = entrySet.getKey();

            switch (key) {
                case "s" -> {
                    if (!TaskUtils.checkNewTaskStatus(params.get(key))) {
                        logParameterValueExc("Unknown value for status parameter");
                    }
                }
                case "to", "fr" -> StringUtils.validateAndParseDateFromString(params.get(key));
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

