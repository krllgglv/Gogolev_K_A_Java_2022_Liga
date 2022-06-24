package ru.digitalleage.gogolev.command;

public enum Command {

    READ(1L),
    CHANGE_S(2L),
    EXIT(10L);
    private Long commandId;

    Command(Long commandId) {
        this.commandId = commandId;
    }

    public Long getCommandId() {
        return commandId;
    }
}
