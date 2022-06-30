package ru.digitalleague.homework4.gogolev.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@AllArgsConstructor
@Data
public class ApplicationError {
    private int statusCode;
    private String message;
    private LocalDateTime timestamp;

}
