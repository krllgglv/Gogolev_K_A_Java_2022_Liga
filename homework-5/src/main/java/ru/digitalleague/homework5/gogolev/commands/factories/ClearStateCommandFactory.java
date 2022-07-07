package ru.digitalleague.homework5.gogolev.commands.factories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.digitalleague.homework5.gogolev.commands.Command;
import ru.digitalleague.homework5.gogolev.commands.commands_for_entities.ClearStateCommand;
import ru.digitalleague.homework5.gogolev.services.TasksService;
import ru.digitalleague.homework5.gogolev.services.UsersService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClearStateCommandFactory implements CommandsAbstractFactory {
    private final UsersService usersService;
    private final TasksService tasksService;

    @Override
    public Command<?> create(String... parameters) {
        return new ClearStateCommand(usersService, tasksService);
    }


}
