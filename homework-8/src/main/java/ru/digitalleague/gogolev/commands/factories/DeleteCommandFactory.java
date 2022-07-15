package ru.digitalleague.gogolev.commands.factories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.digitalleague.gogolev.commands.command.Command;
import ru.digitalleague.gogolev.commands.command.DeleteTaskCommand;
import ru.digitalleague.gogolev.exceptions.InvalidCommandParametersException;
import ru.digitalleague.gogolev.services.TasksService;
import ru.digitalleague.gogolev.util.StringUtils;
import ru.digitalleague.gogolev.util.TaskUtils;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteCommandFactory implements ParametrizedCommandsAbstractFactory {

    private final TasksService tasksService;

    @Override
    public Command<?> create(String... input) {
        if (input.length == 0) {
            logParameterExc("No parameters provided");
        }
        String lineWithParameters = input[0];
        Map<String, String> validatedParameters = validateParameters(lineWithParameters);
        return new DeleteTaskCommand(tasksService, validatedParameters);
    }

    @Override
    public Map<String, String> validateParameters(String input) {
        Map<String, String> params = StringUtils.getParametersFromRequest(input);
        return TaskUtils.validateOnlyIdParameter(params);
    }

    private void logParameterExc(String message) {
        log.error(message);
        throw new InvalidCommandParametersException(message);
    }


}
