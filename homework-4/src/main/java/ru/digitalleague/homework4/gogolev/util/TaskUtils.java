package ru.digitalleague.homework4.gogolev.util;

import lombok.experimental.UtilityClass;
import ru.digitalleague.homework4.gogolev.dto.ExternalTaskDto;
import ru.digitalleague.homework4.gogolev.dto.InternalTaskDto;
import ru.digitalleague.homework4.gogolev.entities.task.Task;
import ru.digitalleague.homework4.gogolev.entities.task.TaskStatus;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

@UtilityClass
public class TaskUtils {
    public static ExternalTaskDto entityToExternalDto(Task task) {
        ExternalTaskDto externalTaskDto = new ExternalTaskDto();
        externalTaskDto.setId(task.getId());
        externalTaskDto.setTitle(task.getTitle());
        externalTaskDto.setDescription(task.getDescription());
        externalTaskDto.setStatus(task.getStatus().getDescription());
        externalTaskDto.setToDate(task.getToDate());
        return externalTaskDto;
    }

    public static InternalTaskDto entityToInternalDto(Task task) {
        InternalTaskDto internalTaskDto = new InternalTaskDto();
        internalTaskDto.setId(task.getId());
        internalTaskDto.setTitle(task.getTitle());
        internalTaskDto.setDescription(task.getDescription());
        internalTaskDto.setStatus(task.getStatus());
        internalTaskDto.setToDate(task.getToDate());
        internalTaskDto.setUserId(task.getUserId());
        return internalTaskDto;
    }

    public static Task internalDtoToEntity(InternalTaskDto dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setToDate(dto.getToDate());
        task.setUserId(dto.getUserId());
        return task;
    }


    public static Task createTaskFromMetadata(String[] metadata) {

        var task = new Task();
        task.setId(Long.parseLong(metadata[0]));
        task.setTitle(metadata[1]);
        task.setDescription(metadata[2]);
        task.setUserId(Long.parseLong(metadata[3]));
        task.setToDate(StringUtils.validateAndParseDateFromString(metadata[4]));
        if (metadata.length == 5) {
            task.setStatus(TaskStatus.NEW);
        } else {
            task.setStatus(TaskStatus.valueOf(metadata[5]));
        }
        return task;
    }

    public static String convertTaskToCSVString(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getId());
        sb.append(", ");
        sb.append(task.getTitle());
        sb.append(", ");
        sb.append(task.getDescription());
        sb.append(", ");
        sb.append(task.getUserId());
        sb.append(", ");
        sb.append(task.getToDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        sb.append(", ");
        sb.append(task.getStatus());
        sb.append("\n");
        return sb.toString();
    }


    public static boolean checkNewTaskStatus(String status) {
        String pattern = Arrays.stream(TaskStatus.values())
                .map(s -> s.getShortName())
                .collect(Collectors.joining("", "[", "]"));
        return status.matches(pattern);
    }

    public static void setTaskStatus(InternalTaskDto task, String status) {
        TaskStatus newTaskStatus = Arrays.stream(TaskStatus.values())
                .filter(s -> s.getShortName().equals(status))
                .findFirst()
                .get();
        task.setStatus(newTaskStatus);
    }


}
