package ru.digitalleague.homework5.gogolev.entities.task;

public enum TaskStatus {
    NEW("Новое", "n"),
    IN_PROGRESS("В работе", "w"),
    DONE("Выполнено", "d");


    private final String description;
    private final String shortName;

    TaskStatus(String description, String shortName) {
        this.description = description;
        this.shortName = shortName;
    }




    public String getDescription() {
        return description;
    }

    public String getShortName() {
        return shortName;
    }
}
