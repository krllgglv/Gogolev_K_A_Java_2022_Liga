package ru.digitalleage.gogolev.entities;

public enum TaskStatus {
    NEW("�����", "n"),
    IN_PROGRESS(" � ������", "w"),
    DONE(" ���������", "d");


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
