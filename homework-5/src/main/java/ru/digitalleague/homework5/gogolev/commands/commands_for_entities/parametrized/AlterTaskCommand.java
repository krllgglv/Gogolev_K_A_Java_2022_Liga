package ru.digitalleague.homework5.gogolev.commands.commands_for_entities.parametrized;

import lombok.extern.slf4j.Slf4j;
import ru.digitalleague.homework5.gogolev.commands.AbstractParametrizedEntityCommand;
import ru.digitalleague.homework5.gogolev.dto.InternalTaskDto;
import ru.digitalleague.homework5.gogolev.exceptions.InvalidCommandParameterValueException;
import ru.digitalleague.homework5.gogolev.exceptions.InvalidCommandParametersException;
import ru.digitalleague.homework5.gogolev.services.TasksService;
import ru.digitalleague.homework5.gogolev.services.UsersService;
import ru.digitalleague.homework5.gogolev.util.StringUtils;
import ru.digitalleague.homework5.gogolev.util.TaskUtils;

import java.util.Map;

@Slf4j
public class AlterTaskCommand extends AbstractParametrizedEntityCommand<Long> {

    public AlterTaskCommand(UsersService usersService, TasksService tasksService, Map<String, String> params) {
        super(usersService, tasksService, params);
    }

    @Override
    public Long execute() {
        Long taskId = StringUtils.getIdFromString(parameters.get("i"));
        InternalTaskDto taskDto = tasksService.findById(taskId);
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "i" -> {}
                case "t" -> taskDto.setTitle(value);
                case "d" -> taskDto.setDescription(value);
                case "u" -> taskDto.setUserId(StringUtils.getIdFromString(value));
                case "s" -> TaskUtils.setTaskStatus(taskDto, value);
                case "da" -> taskDto.setToDate(StringUtils.validateAndParseDateFromString(value));
                default -> logParameterExc("No such parameter " + key);
            }
        }
        Long result = tasksService.save(taskDto);
        usersService.setTasksForUsers();
        return result;
    }


    private void logParameterExc(String message) {
        log.error(message);
        throw new InvalidCommandParametersException(message);
    }

}
