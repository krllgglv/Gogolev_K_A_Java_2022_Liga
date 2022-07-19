package ru.digitalleague.gogolev.entities.task;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.NoSuchElementException;

@Converter(autoApply = true)
public class TaskStatusConverter implements AttributeConverter<TaskStatus, String> {
    @Override
    public String convertToDatabaseColumn(TaskStatus attribute) {
        return attribute.getShortName();
    }

    @Override
    public TaskStatus convertToEntityAttribute(String dbData) {
        return Arrays.stream(TaskStatus.values())
                .filter(it -> it.getShortName().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No such status for task = " + dbData));
    }
}
