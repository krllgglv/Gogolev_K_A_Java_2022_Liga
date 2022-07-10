package ru.digitalleague.gogolev.commands.commands_for_entities.parametrized;

import lombok.extern.slf4j.Slf4j;
import ru.digitalleague.gogolev.commands.AbstractParametrizedEntityCommand;
import ru.digitalleague.gogolev.dto.ExternalTaskDto;
import ru.digitalleague.gogolev.dto.UserDto;
import ru.digitalleague.gogolev.services.TasksService;
import ru.digitalleague.gogolev.services.UsersService;

import java.util.List;
import java.util.Map;

@Slf4j
public class ReadUsersTasksCommand extends AbstractParametrizedEntityCommand<List<ExternalTaskDto>> {
    public ReadUsersTasksCommand(UsersService usersService, TasksService tasksService, Map<String, String> params) {
        super(usersService, tasksService, params);
    }


    @Override
    public List<ExternalTaskDto> execute() {
        Long id = Long.parseLong(parameters.get("i"));
        UserDto userDto = usersService.findById(id);
        return userDto.getTasks();
    }


}
