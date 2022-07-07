package ru.digitalleague.homework5.gogolev.dto;


import lombok.Data;
import ru.digitalleague.homework5.gogolev.entities.task.TaskStatus;

import java.time.LocalDate;

@Data
public class InternalTaskDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Long userId;
    private LocalDate toDate;
}
