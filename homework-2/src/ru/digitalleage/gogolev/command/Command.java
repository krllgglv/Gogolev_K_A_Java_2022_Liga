package ru.digitalleage.gogolev.command;

public enum Command {

    READ(1L),
    CHANGE_S(2L),
    DELETE(3L),
    ADD(4L),
    ALTER(5L),
    SAVE_STATE_TO_FILE(6L),
    CLEAR_STATE(7L),
    HELP(8L),
    EXIT(10L);
    private final Long commandId;

    Command(Long commandId) {
        this.commandId = commandId;
    }

    public Long getCommandId() {
        return commandId;
    }
}
