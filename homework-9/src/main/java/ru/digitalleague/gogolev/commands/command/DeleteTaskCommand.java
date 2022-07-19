package ru.digitalleague.gogolev.commands.command;

import lombok.extern.slf4j.Slf4j;
import ru.digitalleague.gogolev.services.TasksService;

import java.util.Map;


@Slf4j
public class DeleteTaskCommand extends AbstractParametrizedCommand<Boolean> {
    private final TasksService tasksService;

    public DeleteTaskCommand(TasksService tasksService, Map<String, String> params) {
        super(params);
        this.tasksService = tasksService;
    }


    @Override
    public Boolean execute() {
        Long taskId = Long.parseLong(parameters.get("i"));
        tasksService.deleteTask(taskId);
        log.info("deleted task with id =" + taskId);
        return true;
    }
}
