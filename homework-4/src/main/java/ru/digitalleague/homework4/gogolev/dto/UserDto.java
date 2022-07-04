package ru.digitalleague.homework4.gogolev.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String name;
    private List<ExternalTaskDto> tasks;
}
