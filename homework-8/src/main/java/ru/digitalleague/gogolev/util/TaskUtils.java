package ru.digitalleague.gogolev.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.digitalleague.gogolev.dto.ExternalTaskDto;
import ru.digitalleague.gogolev.dto.InternalTaskDto;
import ru.digitalleague.gogolev.entities.task.Task;
import ru.digitalleague.gogolev.entities.task.TaskStatus;
import ru.digitalleague.gogolev.exceptions.InvalidCommandParameterValueException;
import ru.digitalleague.gogolev.exceptions.InvalidCommandParametersException;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
@Slf4j
public class TaskUtils {
    public static ExternalTaskDto entityToExternalDto(Task task) {
        ExternalTaskDto externalTaskDto = new ExternalTaskDto();
        externalTaskDto.setId(task.getId());
        externalTaskDto.setTitle(task.getTitle());
        externalTaskDto.setDescription(task.getDescription());
        externalTaskDto.setStatus(task.getStatus().getDescription());
        externalTaskDto.setDateTo(task.getDateTo());
        return externalTaskDto;
    }


    public static String convertTaskToCSVString(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getId());
        sb.append(", ");
        sb.append(task.getTitle());
        sb.append(", ");
        sb.append(task.getDescription());
        sb.append(", ");
        sb.append(task.getUser().getId());
        sb.append(", ");
        sb.append(task.getDateTo().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        sb.append(", ");
        sb.append(task.getStatus().getShortName());
        sb.append("\n");
        return sb.toString();
    }


    public static boolean checkNewTaskStatus(String status) {
        String pattern = Arrays.stream(TaskStatus.values())
                .map(TaskStatus::getShortName)
                .collect(Collectors.joining("", "[", "]"));
        return status.matches(pattern);
    }

    public static void setTaskStatus(InternalTaskDto task, String status) {
        TaskStatus newTaskStatus = Arrays.stream(TaskStatus.values())
                .filter(s -> s.getShortName().equals(status))
                .findFirst()
                .orElseThrow(() -> new InvalidCommandParameterValueException("No such status"));
        task.setStatus(newTaskStatus);
    }

    public Map<String, String> validateOnlyIdParameter(Map<String, String> params) {
        if (params.size() != 1) {
            logParameterExc("Wrong quantity of parameters, expected exactly 1");
        }

        if (!params.containsKey("i")) {
            logParameterExc("Parameter i is absent");
        }
        if (!StringUtils.validateIdFromString(params.get("i"))) {
            logParameterValueExc("Value of parameter i should be positive digit");
        }
        return params;
    }

    private void logParameterExc(String message) {
        log.error(message);
        throw new InvalidCommandParametersException(message);
    }

    private void logParameterValueExc(String message) {
        log.error(message);
        throw new InvalidCommandParameterValueException(message);
    }


}
