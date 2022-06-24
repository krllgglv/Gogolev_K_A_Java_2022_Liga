package ru.digitalleage.gogolev;


import ru.digitalleage.gogolev.command.Command;
import ru.digitalleage.gogolev.console.ConsoleHandler;
import ru.digitalleage.gogolev.entities.Task;
import ru.digitalleage.gogolev.entities.TaskStatus;
import ru.digitalleage.gogolev.entities.User;
import ru.digitalleage.gogolev.storage.Storage;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class Application {
    private Storage storage;
    private ConsoleHandler consoleHandler;

    public Application(Storage storage) {
        this.consoleHandler = new ConsoleHandler();
        this.storage = storage;

    }

    public void run() {
        System.out.println(" ������� �������");
        while (true) {

            Long[] signal = consoleHandler.handleCommand();
            if (signal[0] == -1L) {
                System.out.println(" ������� ���������� �������");
                continue;
            }

            var mayBeCommand = getCommandById(signal[0]);
            if (!mayBeCommand.isPresent()) {
                System.out.println(" ������������ �������. ��������� ����");
                continue;
            }
            switch (mayBeCommand.get()) {
                case READ -> readTasksForUser(signal[1]);
                case CHANGE_S -> changeTaskStatus(signal[1]);
                case EXIT -> shutdownApp();
            }
        }
    }

    private void changeTaskStatus(Long taskId) {
        Optional<Task> mayBeTask = storage.findTaskById(taskId);
        if (mayBeTask.isEmpty()) {
            String.format(" ������ � id = %d �� �������", taskId);
            return;
        }
        Task currentTask = mayBeTask.get();
        while (true) {
            System.out.println(" ������� ����� ������ ������: w - � ������, n - �����, d - ��������� ");
            String newStatusDescription = consoleHandler.readLine();
            String pattern = Arrays.stream(TaskStatus.values())
                    .map(s -> s.getShortName())
                    .collect(Collectors.joining("", "[", "]"));
            if (newStatusDescription.matches(pattern)) {
                TaskStatus newTaskStatus = Arrays.stream(TaskStatus.values())
                        .filter(s -> s.getShortName().equals(newStatusDescription))
                        .findFirst()
                        .get();
                currentTask.setStatus(newTaskStatus);
                String.format(" ������ ������ � id = %d �������", taskId);
                return;
            }

        }
    }

    private void shutdownApp() {
        System.exit(0);
    }

    private void readTasksForUser(Long userId) {
        Optional<User> mayBeUser = storage.findUserById(userId);
        if (mayBeUser.isPresent()) {
            mayBeUser.get().getTasks().stream()
                    .forEach(System.out::println);
        } else {
            System.out.println(String.format(" ������������ � id = %d �� ������", userId));
        }
    }

    private Optional<Command> getCommandById(Long id) {
        return Arrays.stream(Command.values())
                .filter(c -> c.getCommandId() == id)
                .findFirst();
    }
}
