package ru.digitalleague.gogolev.commands.command;

import java.util.Map;

public abstract class AbstractParametrizedCommand<R> implements Command<R>{
    protected Map<String, String > parameters;

    protected AbstractParametrizedCommand(Map<String, String > parameters) {
        this.parameters = parameters;
    }
}
