package ru.digitalleague.gogolev.commands.command;

import lombok.extern.slf4j.Slf4j;
import ru.digitalleague.gogolev.dto.ExternalTaskDto;
import ru.digitalleague.gogolev.entities.user.User;
import ru.digitalleague.gogolev.services.UsersService;
import ru.digitalleague.gogolev.util.UserUtils;

import java.util.List;
import java.util.Map;

@Slf4j
public class ReadUsersTasksCommand extends AbstractParametrizedCommand<List<ExternalTaskDto>> {
    private final UsersService usersService;

    public ReadUsersTasksCommand(UsersService usersService, Map<String, String> params) {
        super(params);
        this.usersService = usersService;
    }


    @Override
    public List<ExternalTaskDto> execute() {
        Long id = Long.parseLong(parameters.get("i"));

        User user = usersService.findById(id);
        return UserUtils.entityToDto(user).getTasks();
    }


}
