package ru.digitalleague.gogolev.commands.commands_for_entities.parametrized;

import lombok.extern.slf4j.Slf4j;
import ru.digitalleague.gogolev.commands.AbstractParametrizedEntityCommand;
import ru.digitalleague.gogolev.services.TasksService;
import ru.digitalleague.gogolev.services.UsersService;

import java.util.Map;


@Slf4j
public class DeleteTaskCommand extends AbstractParametrizedEntityCommand<Boolean> {
    public DeleteTaskCommand(UsersService usersService, TasksService tasksService, Map<String, String> params) {
        super(usersService, tasksService, params);
    }


    @Override
    public Boolean execute() {
        Long taskId = Long.parseLong(parameters.get("i"));
        boolean result = tasksService.deleteTask(taskId);
        usersService.setTasksForUsers();
        log.info("deleted task with id =" + taskId);
        return result;
    }
}
