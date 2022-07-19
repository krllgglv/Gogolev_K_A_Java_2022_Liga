package ru.digitalleague.gogolev.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class ExternalTaskDto {
    private Long id;
    private String title;
    private String description;
    private String  status;
    private LocalDate dateTo;
}
