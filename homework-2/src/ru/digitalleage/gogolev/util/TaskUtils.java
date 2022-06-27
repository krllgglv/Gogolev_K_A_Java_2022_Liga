package ru.digitalleage.gogolev.util;

import ru.digitalleage.gogolev.entities.Task;
import ru.digitalleage.gogolev.entities.TaskStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class TaskUtils {
    private TaskUtils() {
    }

    public static Task createTaskFromMetadata(String[] metadata) {

        var task = new Task();
        task.setId(Long.parseLong(metadata[0]));
        task.setTitle(metadata[1]);
        task.setDescription(metadata[2]);
        task.setUserId(Long.parseLong(metadata[3]));
        task.setToDate(TimeUtils.parseDateFromString(metadata[4]));
        if (metadata.length == 5) {
            task.setStatus(TaskStatus.NEW);
        } else {
            task.setStatus(TaskStatus.valueOf(metadata[5]));
        }
        return task;
    }

    public static String convertTaskToCSVString(Task task){
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

    public static void setTaskStatus(Task task, String status) {
        TaskStatus newTaskStatus = Arrays.stream(TaskStatus.values())
                .filter(s -> s.getShortName().equals(status))
                .findFirst()
                .get();
        task.setStatus(newTaskStatus);
        System.out.println(String.format(" Статус задачи с id = %d изменен", task.getId()));
    }

    public static void setTaskTitle(Task task, String newValueForField) {
        task.setTitle(newValueForField);
        System.out.println(String.format(" Заголовок задачи с id = %d изменен", task.getId()));
    }

    public static void setTaskDescription(Task task, String newValueForField) {
        task.setDescription(newValueForField);
        System.out.println(String.format("Описание задачи с id = %d изменено", task.getId()));
    }

    public static void setTasksUserId(Task task, String newValueForField) {
        Long newId = -1L;
        try {
            newId = Long.parseLong(newValueForField);
        } catch (NumberFormatException e) {
            System.out.println("Идентификатор должен быть целым числом");
            return;
        }
        task.setUserId(newId);
        System.out.println(String.format("Идентификатор пользователя для задачи с id = %d изменен", task.getId()));
    }

    public static void setTaskToDate(Task task, String newValueForField) {
        LocalDate newDate;
        try {
            newDate = TimeUtils.parseDateFromString(newValueForField);
            task.setToDate(newDate);
            System.out.println(String.format("Срок выполнения для задачи с id = %d изменен", task.getId()));
        } catch (DateTimeParseException e) {
            System.out.println("Введен неверный формат даты, корректный пример: ДД.ММ.ГГГГ");
        }
    }

    public static boolean isTaskValid(Task task) {
        return  task.getId() != null &&
                task.getUserId() != null &&
                task.getDescription() != null &&
                task.getTitle() != null;
    }

}
