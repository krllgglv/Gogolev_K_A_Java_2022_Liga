package ru.digitalleague.homework5.gogolev.commands.commands_for_entities.parametrized;

import lombok.extern.slf4j.Slf4j;
import ru.digitalleague.homework5.gogolev.commands.AbstractParametrizedEntityCommand;
import ru.digitalleague.homework5.gogolev.services.TasksService;
import ru.digitalleague.homework5.gogolev.services.UsersService;
import ru.digitalleague.homework5.gogolev.util.StringUtils;
import ru.digitalleague.homework5.gogolev.util.TaskUtils;

import java.util.Map;


@Slf4j
public class DeleteTaskCommand extends AbstractParametrizedEntityCommand<Boolean> {
    public DeleteTaskCommand(UsersService usersService, TasksService tasksService, String input) {
        super(usersService, tasksService, input);
    }


    @Override
    protected Map<String, String> validateAndGetParameters(String input) {
        Map<String, String> params = StringUtils.getParametersFromRequest(input);
        return validateParameters(params);
    }

    private Map<String, String> validateParameters(Map<String, String> params) {
        return TaskUtils.validateOnlyIdParameter(params);
    }

    @Override
    public Boolean execute() {
        Long taskId = Long.parseLong(parameters.get("id"));
        boolean result = tasksService.deleteTask(taskId);
        usersService.setTasksForUsers();
        log.info("deleted task with id =" + taskId);
        return result;
    }
}
