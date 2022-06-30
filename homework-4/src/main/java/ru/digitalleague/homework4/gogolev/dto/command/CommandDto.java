package ru.digitalleague.homework4.gogolev.dto.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class CommandDto {
    private String name;
    private Map<String, String> arguments;
}
