package ru.digitalleague.gogolev.commands.factories;

import java.util.Map;

public interface ParametrizedCommandsAbstractFactory extends CommandsAbstractFactory {

     Map<String, String> validateParameters(String input);

}
