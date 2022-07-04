package ru.digitalleague.homework4.gogolev.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.digitalleague.homework4.gogolev.dto.command.CommandDto;
import ru.digitalleague.homework4.gogolev.dto.command.CommandDescription;
import ru.digitalleague.homework4.gogolev.exceptions.InvalidCommandParameterValueException;
import ru.digitalleague.homework4.gogolev.exceptions.InvalidCommandParametersException;
import ru.digitalleague.homework4.gogolev.exceptions.NoSuchCommandException;
import ru.digitalleague.homework4.gogolev.util.StringUtils;
import ru.digitalleague.homework4.gogolev.util.TaskUtils;

import java.util.Collections;
import java.util.Map;
@Slf4j
@Service

public class CommandHandlerService {
    @Value("${check.pattern.alterParams}")
    private String alterCommandParamsPattern;

    @Value("${check.pattern.addParams}")
    private String addCommandParamsPattern;


    public CommandDto handleCommand(String commandName, Map<String, String> parameters) {

        CommandDescription commandDescription;
        try {
            commandDescription = CommandDescription.valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            log.error(String.format("No such command with name = %s. Повторите запрос", commandName));
            throw new NoSuchCommandException(String.format("No such command with name = %s. Повторите запрос", commandName));
        }

        return switch (commandDescription) {
            case READ -> handleReadCommand(CommandDescription.READ, parameters);
            case DELETE -> handleDeleteCommand(CommandDescription.DELETE, parameters);
            case ADD -> handleAddCommand(CommandDescription.ADD, parameters);
            case ALTER -> handleAlterCommand(CommandDescription.ALTER, parameters);
            case SAVE_STATE_TO_FILE -> handleCommandWithNoParameters(CommandDescription.SAVE_STATE_TO_FILE);
            case CLEAR_STATE -> handleCommandWithNoParameters(CommandDescription.CLEAR_STATE);
            case HELP -> handleCommandWithNoParameters(CommandDescription.HELP);
            case EXIT -> handleCommandWithNoParameters(CommandDescription.EXIT);
        };

    }

    private CommandDto handleReadCommand(CommandDescription commandDescription, Map<String, String> params) {
        return getCommandDtoWithOnlyIdParameter(commandDescription, params);
    }

    private CommandDto handleAddCommand(CommandDescription commandDescription, Map<String, String> params) {
        validateAddParameters(params);
        return new CommandDto(commandDescription.name(), params);
    }


    private CommandDto handleAlterCommand(CommandDescription commandDescription, Map<String, String> params) {
        validateAlterParameters(params);
        return new CommandDto(commandDescription.name(), params);
    }


    private CommandDto handleDeleteCommand(CommandDescription commandDescription, Map<String, String> params) {
        return getCommandDtoWithOnlyIdParameter(commandDescription, params);
    }


    private CommandDto handleCommandWithNoParameters(CommandDescription commandDescription) {
        return getCommandDtoWithNoParameters(commandDescription);
    }

    private CommandDto getCommandDtoWithOnlyIdParameter(CommandDescription commandDescription, Map<String, String> params) {
        if (params.isEmpty()) {
            log.error("no parameters provided, expected 1");
            throw new InvalidCommandParametersException("no parameters provided, expected 1");
        }
        if (params.size() > 1) {
            log.error("To many parameters, expected 1");
            throw new InvalidCommandParametersException("To many parameters, expected 1");
        }

        if (params.containsKey("id")) {
            StringUtils.validateAndGetIdFromString((params.get("id")));
        }
        return new CommandDto(commandDescription.name(), params);
    }

    private void validateAlterParameters(Map<String, String> params) {
        if (params.isEmpty()) {
            log.error("no parameters provided, expected at least 1");
            throw new InvalidCommandParametersException("no parameters provided, expected at least 1");
        }
        if (params.size() > 6) {
            log.error("To many parameters, expected max 6");
            throw new InvalidCommandParametersException("To many parameters, expected max 6");
        }
        if (!params.containsKey("i")) {
            log.error("!!Id of task is absent!!");
            throw new InvalidCommandParametersException("!!Id of task is absent!!");
        }
        for (String param : params.keySet()) {
            if (!param.matches(alterCommandParamsPattern)) {
                log.error(String.format("Unknown parameter %s.", param));
                throw new InvalidCommandParametersException(String.format("Unknown parameter %s." +
                        "Use i - to selecet task for altering, use t - to change title, d - to change description," +
                        " u - to change user id," +
                        "s - to change state, da - to change date", param));
            }
        }
        for (Map.Entry<String, String> entrySet : params.entrySet()) {
            String key = entrySet.getKey();

            switch (key) {
                case "i" -> StringUtils.validateAndGetIdFromString(params.get(key));
                case "t" -> {
                    if (params.get(key).isEmpty()) {
                        log.error("The value of parameter t should not  be empty");
                        throw new InvalidCommandParameterValueException
                                ("The value of parameter t should not  be empty");
                    }
                }
                case "d" -> {
                    if (params.get(key).isEmpty()){
                        log.error("The value of parameter d should not be empty");
                        throw new InvalidCommandParameterValueException
                                ("The value of parameter d should not be empty");
                    }
                }
                case "s" -> {
                    if (!TaskUtils.checkNewTaskStatus(params.get(key))){
                        log.error("Unknown value for status parameter");
                        throw new InvalidCommandParameterValueException(String.format("Unknown value of status parameter %s." +
                                "Use n - to set in NEW status, w - to set in IN_PROGRESS status," +
                                " d - to set in DONE status", params.get(key)));
                    }
                }
                case "u" -> StringUtils.validateAndGetIdFromString(params.get(key));
                case "da" -> StringUtils.validateAndParseDateFromString(params.get(key));
            }
        }
    }


    private void validateAddParameters(Map<String, String> params) {

        if (params.size() != 4) {
            log.error("Wrong quantity of parameters, expected exactly 4");
            throw new InvalidCommandParametersException("Wrong quantity of parameters, expected exactly 4");
        }
        for (String param : params.keySet()) {
            if (!param.matches(addCommandParamsPattern)) {
                log.error(String.format("Unknown parameter %s.", param));
                throw new InvalidCommandParametersException(String.format("Unknown parameter %s." +
                        "Use t - to set title, d - to set description, u - to set user id," +
                        "da - to set date", param));
            }
        }
        for (Map.Entry<String, String> entrySet : params.entrySet()) {
            String key = entrySet.getKey();

            switch (key) {
                case "t" -> {
                    if (params.get(key).isEmpty()){
                        log.error("The value of parameter t should not  be empty");
                        throw new InvalidCommandParameterValueException
                                ("The value of parameter t should not  be empty");
                    }
                }
                case "d" -> {
                    if (params.get(key).isEmpty()) {
                        log.error("The value of parameter d should not be empty");
                        throw new InvalidCommandParameterValueException
                                ("The value of parameter d should not be empty");
                    }
                }
                case "u" -> StringUtils.validateAndGetIdFromString(params.get(key));
                case "da" -> StringUtils.validateAndParseDateFromString(params.get(key));
            }
        }
    }


    private CommandDto getCommandDtoWithNoParameters(CommandDescription commandDescription) {
        return new CommandDto(commandDescription.name(), Collections.emptyMap());
    }


}