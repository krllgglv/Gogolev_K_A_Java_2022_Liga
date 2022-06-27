package ru.digitalleage.gogolev.console;

import ru.digitalleage.gogolev.command.Command;
import ru.digitalleage.gogolev.util.StringUtils;

import java.util.Optional;
import java.util.Scanner;

public class ConsoleHandler {
    private final Scanner scanner = new Scanner(System.in);

    public String readLine() {
        return scanner.nextLine();
    }

    public Long[] handleCommand() {
        Long[] signal = {-1L, -1L};
        String input = readLine();
        Command command;
        if (getCommandFromCommandName(input).isPresent()) {
            command = getCommandFromCommandName(input).get();
        } else {
            return signal;
        }

        return switch (command) {
            case READ -> handleReadCommand(signal, input);
            case CHANGE_S -> handleChangeSCommand(signal, input);
            case DELETE -> handleDeleteCommand(signal, input);
            case ADD -> handleAddCommand(signal);
            case ALTER -> handleAlterCommand(signal, input);
            case SAVE_STATE_TO_FILE -> handleSaveStateCommand(signal);
            case CLEAR_STATE -> handleClearStateCommand(signal);
            case HELP -> handleHelpCommand(signal, input);
            case EXIT -> handleExitCommand(signal);
        };

    }

    private Long[] handleExitCommand(Long[] signal) {
        return handleCommandWhichDoesntRequireIdOFResource(signal, Command.EXIT);
    }

    private Long[] handleSaveStateCommand(Long[] signal) {
        return handleCommandWhichDoesntRequireIdOFResource(signal, Command.SAVE_STATE_TO_FILE);
    }

    private Long[] handleClearStateCommand(Long[] signal) {
        return handleCommandWhichDoesntRequireIdOFResource(signal, Command.CLEAR_STATE);
    }

    private Long[] handleHelpCommand(Long[] signal, String input) {
        return handleCommandWhichDoesntRequireIdOFResource(signal, Command.HELP);
    }

    private Long[] handleReadCommand(Long[] signal, String input) {
        Long[] output = handleCommandWhichRequiresIdOFResource(signal, input, Command.READ);
        if (output[1] == -1L) {
            System.out.println("введите id пользователя. Пример: read -id=1");
        }
        return output;
    }

    private Long[] handleAddCommand(Long[] signal) {
         return handleCommandWhichDoesntRequireIdOFResource(signal, Command.ADD);
    }

    private Long[] handleChangeSCommand(Long[] signal, String input) {
        Long[] output = handleCommandWhichRequiresIdOFResource(signal, input, Command.CHANGE_S);
        if (output[1] == -1L) {
            System.out.println("введите id задачи. Пример: change_s -id=1");
        }
        return output;
    }

    private Long[] handleAlterCommand(Long[] signal, String input) {
        Long[] output = handleCommandWhichRequiresIdOFResource(signal, input, Command.ALTER);
        if (output[1] == -1L) {
            System.out.println("введите id задачи. Пример: alter -id=1");
        }
        return output;
    }


    private Long[] handleDeleteCommand(Long[] signal, String input) {
        Long[] output = handleCommandWhichRequiresIdOFResource(signal, input, Command.DELETE);
        if (output[1] == -1L) {
            System.out.println("введите id задачи. Пример: delete -id=1");
        }
        return output;
    }

    private Long[] handleCommandWhichRequiresIdOFResource(Long[] signal, String input, Command command) {
        Long id = StringUtils.getIdFromString(input);
        if (id != -1L) {
            signal[0] = command.getCommandId();
            signal[1] = id;
            return signal;
        } else {
            return signal;
        }
    }

    private Long[] handleCommandWhichDoesntRequireIdOFResource(Long[] signal, Command command) {
        signal[0] = command.getCommandId();
        return signal;

    }

    private Optional<Command> getCommandFromCommandName(String input) {
        Command command = null;
        String mayBeCommandName = input.split(" ")[0].toUpperCase();
        try {
            command = Command.valueOf(mayBeCommandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("Отсутствует команда с именем: " + mayBeCommandName + ".");
        }
        return Optional.ofNullable(command);
    }
}