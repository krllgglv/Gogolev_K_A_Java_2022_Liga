package ru.digitalleague.homework5.gogolev.commands.commands_for_entities.parametrized;

import lombok.extern.slf4j.Slf4j;
import ru.digitalleague.homework5.gogolev.commands.AbstractParametrizedEntityCommand;
import ru.digitalleague.homework5.gogolev.dto.ExternalTaskDto;
import ru.digitalleague.homework5.gogolev.dto.UserDto;
import ru.digitalleague.homework5.gogolev.services.TasksService;
import ru.digitalleague.homework5.gogolev.services.UsersService;
import ru.digitalleague.homework5.gogolev.util.StringUtils;
import ru.digitalleague.homework5.gogolev.util.TaskUtils;

import java.util.List;
import java.util.Map;

@Slf4j
public class ShowUsersTasksCommand extends AbstractParametrizedEntityCommand<List<ExternalTaskDto>> {
    public ShowUsersTasksCommand(UsersService usersService, TasksService tasksService, String input) {
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
    public List<ExternalTaskDto> execute() {

        Long id = Long.parseLong(parameters.get("i"));
        UserDto userDto = usersService.findById(id);
        return userDto.getTasks();
    }
}
