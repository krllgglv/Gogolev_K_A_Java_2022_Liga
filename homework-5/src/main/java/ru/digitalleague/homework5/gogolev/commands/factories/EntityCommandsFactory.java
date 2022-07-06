package ru.digitalleague.homework5.gogolev.commands.factories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.digitalleague.homework5.gogolev.commands.Command;
import ru.digitalleague.homework5.gogolev.commands.commands_for_entities.ClearStateCommand;
import ru.digitalleague.homework5.gogolev.commands.commands_for_entities.SaveStateCommand;
import ru.digitalleague.homework5.gogolev.commands.commands_for_entities.parametrized.AlterTaskCommand;
import ru.digitalleague.homework5.gogolev.commands.commands_for_entities.parametrized.CreateTaskCommand;
import ru.digitalleague.homework5.gogolev.commands.commands_for_entities.parametrized.DeleteTaskCommand;
import ru.digitalleague.homework5.gogolev.commands.commands_for_entities.parametrized.ShowUsersTasksCommand;
import ru.digitalleague.homework5.gogolev.exceptions.NoSuchCommandException;
import ru.digitalleague.homework5.gogolev.services.TasksService;
import ru.digitalleague.homework5.gogolev.services.UsersService;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntityCommandsFactory implements CommandsAbstractFactory {
    private final TasksService tasksService;
    private final UsersService usersService;

    @Override
    public Command<?> create(String input) {

        String[] strings = input.split("_");
        if (strings.length == 0) {
            String message = "No command name provided";
            handleException(message);
        }
        String commandName = input.split("_")[0];
        if (commandName.isBlank()) {
            String message = "Command name should not be empty";
            handleException(message);
        }

        return switch (commandName.toUpperCase()) {
            case "ADD" -> new CreateTaskCommand(usersService, tasksService, input);
            case "ALTER" -> new AlterTaskCommand(usersService, tasksService, input);
            case "DELETE" -> new DeleteTaskCommand(usersService, tasksService, input);
            case "READ" -> new ShowUsersTasksCommand(usersService, tasksService, input);
            case "CLEAR" -> new ClearStateCommand(usersService, tasksService);
            case "SAVE" -> new SaveStateCommand(usersService, tasksService);
            default -> {
                String message = "No such command" + commandName;
                log.error(message);
                throw new NoSuchCommandException(message);
            }
        };
    }

    private void handleException(String message) {
        log.error(message);
        throw new NoSuchCommandException(message);
    }

}
