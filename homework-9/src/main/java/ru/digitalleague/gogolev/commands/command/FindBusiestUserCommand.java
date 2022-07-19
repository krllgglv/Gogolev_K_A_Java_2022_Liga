package ru.digitalleague.gogolev.commands.command;

import org.springframework.data.jpa.domain.Specification;
import ru.digitalleague.gogolev.dto.UserDto;
import ru.digitalleague.gogolev.entities.task.Task;
import ru.digitalleague.gogolev.services.UsersService;
import ru.digitalleague.gogolev.util.TasksSpecificationProvider;

import java.util.Map;

public class FindBusiestUserCommand extends AbstractParametrizedCommand<UserDto> {
    private final UsersService usersService;

    public FindBusiestUserCommand(UsersService usersService, Map<String, String> parameters) {
        super(parameters);
        this.usersService = usersService;
    }

    @Override
    public UserDto execute() {
        Specification<Task> tasksSpecification = TasksSpecificationProvider.getTasksSpecification(parameters);
        return usersService.findUserWithMaxQuantityOfTasks(tasksSpecification);
    }
}
