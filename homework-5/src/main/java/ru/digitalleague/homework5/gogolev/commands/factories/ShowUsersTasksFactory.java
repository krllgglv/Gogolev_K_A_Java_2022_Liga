package ru.digitalleague.homework5.gogolev.commands.factories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.digitalleague.homework5.gogolev.commands.AbstractParametrizedEntityCommand;
import ru.digitalleague.homework5.gogolev.commands.Command;
import ru.digitalleague.homework5.gogolev.commands.commands_for_entities.parametrized.AddTaskCommand;
import ru.digitalleague.homework5.gogolev.exceptions.InvalidCommandParametersException;
import ru.digitalleague.homework5.gogolev.services.TasksService;
import ru.digitalleague.homework5.gogolev.services.UsersService;
import ru.digitalleague.homework5.gogolev.util.StringUtils;
import ru.digitalleague.homework5.gogolev.util.TaskUtils;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShowUsersTasksFactory implements ParametrizedCommandsAbstractFactory {

    private final UsersService usersService;
    private final TasksService tasksService;
    @Override
    public Command<?> create(String... input) {
        if (input.length == 0) {
            logParameterExc("No parameters provided");
        }
        String lineWithParameters = input[0];
        Map<String, String> validatedParameters = validateParameters(lineWithParameters);
        return new AddTaskCommand(usersService, tasksService, validatedParameters);
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
