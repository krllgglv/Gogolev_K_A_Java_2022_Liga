package ru.digitalleague.gogolev.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.digitalleague.gogolev.entities.task.TaskStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternalTaskDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Long userId;
    private LocalDate toDate;
}
