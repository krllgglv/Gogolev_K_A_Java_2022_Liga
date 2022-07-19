package ru.digitalleague.gogolev.commands.command;

public enum CommandsDescription {

    READ("readCommandFactory"),
    DELETE("deleteCommandFactory"),
    ADD("addCommandFactory"),
    ALTER("alterCommandFactory"),
    CLEAR("clearStateCommandFactory"),
    SAVE("saveCommandFactory"),
    FINDB("findBusiestUserCommandFactory"),
    HELP("helpCommandFactory"),
    EXIT("exitCommandFactory");

    private String factoryName;

    CommandsDescription(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getFactoryName() {
        return factoryName;
    }
}
