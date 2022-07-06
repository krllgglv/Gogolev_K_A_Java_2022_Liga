package ru.digitalleague.homework5.gogolev.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String name;
    private List<ExternalTaskDto> tasks;
}
