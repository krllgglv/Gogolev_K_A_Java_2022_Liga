package ru.digitalleague.homework4.gogolev.entities.task;

public enum TaskStatus {
    NEW("Новое", "n"),
    IN_PROGRESS("В работе", "w"),
    DONE("Выполнено", "d");


    private String description;
    private String shortName;

    TaskStatus(String description, String shortName) {
        this.description = description;
        this.shortName = shortName;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public String getShortName() {
        return shortName;
    }
}
