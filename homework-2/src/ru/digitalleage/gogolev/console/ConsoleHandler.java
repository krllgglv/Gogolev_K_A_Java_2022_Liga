package ru.digitalleage.gogolev.console;

import ru.digitalleage.gogolev.command.Command;
import ru.digitalleage.gogolev.util.StringUtils;

import java.util.Scanner;

public class ConsoleHandler {
    private final Scanner scanner = new Scanner(System.in);

    public Long[] handleCommand() {
        Long[] signal = {-1L, -1L};
        String line = scanner.nextLine();

        if (line.toUpperCase().startsWith(Command.READ.name())) {
            Long id = StringUtils.getIdFromString(line);
            if (id != -1L) {
                signal[0] = Command.READ.getCommandId();
                signal[1] = id;
                return signal;
            } else {
                System.out.println("введите id пользователя. Пример: -id=1");
                return signal;
            }
        } else if (line.toUpperCase().startsWith(Command.EXIT.name())) {
            signal[0] = Command.EXIT.getCommandId();
            return signal;

        } else if (line.toUpperCase().startsWith(Command.CHANGE_S.name())) {
            Long id = StringUtils.getIdFromString(line);
            if (id != -1L) {
                signal[0] = Command.CHANGE_S.getCommandId();
                signal[1] = id;
                return signal;
            } else {
                System.out.println("введите id пользователя. Пример: -id=1");
                return signal;
            }
        }
        return signal;

    }

    public String readLine() {
        return scanner.nextLine();
    }
}