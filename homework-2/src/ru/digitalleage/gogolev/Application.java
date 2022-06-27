package ru.digitalleage.gogolev;


import ru.digitalleage.gogolev.command.Command;
import ru.digitalleage.gogolev.console.ConsoleHandler;
import ru.digitalleage.gogolev.entities.Task;
import ru.digitalleage.gogolev.entities.TaskStatus;
import ru.digitalleage.gogolev.entities.User;
import ru.digitalleage.gogolev.repository.TasksRepository;
import ru.digitalleage.gogolev.repository.UsersRepository;
import ru.digitalleage.gogolev.util.CommandUtil;
import ru.digitalleage.gogolev.util.StringUtils;
import ru.digitalleage.gogolev.util.TaskUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public class Application {
    private static final String PATH_TO_HELP_FILE = "resources/help.txt";
    private final TasksRepository tasksRepository;
    private final UsersRepository usersRepository;
    private final ConsoleHandler consoleHandler;

    public Application(TasksRepository tasksRepository, UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.consoleHandler = new ConsoleHandler();
        this.tasksRepository = tasksRepository;
        setTasksForUsers();
    }

    public void run() {
        while (true) {
            System.out.println("Введите команду или help для показа списка доступных команд");

            Long[] signal = consoleHandler.handleCommand();
            if (signal[0] == -1L) {
                continue;
            }

            var mayBeCommand = CommandUtil.getCommandById(signal[0]);
            if (mayBeCommand.isEmpty()) {
                System.out.println(" Некорректная команда. Повторите ввод");
                continue;
            }
            switch (mayBeCommand.get()) {
                case READ -> readTasksForUser(signal[1]);
                case ADD -> addNewTask();
                case CHANGE_S -> changeTaskStatus(signal[1]);
                case EXIT -> shutdownApp();
                case DELETE -> deleteTask(signal[1]);
                case ALTER -> alterTask(signal[1]);
                case CLEAR_STATE -> clearState();
                case SAVE_STATE_TO_FILE -> saveStateToFiles();
                case HELP -> showHelpPage();

            }
        }
    }

    private void showHelpPage() {
        try {
            Files.lines(Path.of(PATH_TO_HELP_FILE))
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("!Не удалось прочитать файл help!");
        }
    }

    private void saveStateToFiles() {
        tasksRepository.saveStateToFile();
    }

    private void clearState() {
        System.out.println("Для подтверждения очистки хранилища введите y, для отмены операции любую букву");
        String input = consoleHandler.readLine();
        if (input.equalsIgnoreCase("Y")) {
            tasksRepository.clearState();
            usersRepository.clearState();
            System.out.println("Хранилище полность очищено");
        }
    }

    private void alterTask(Long taskId) {
        Optional<Task> mayBeTask = tasksRepository.findTaskById(taskId);
        if (mayBeTask.isEmpty()) {
            String.format(" Задача с id = %d не найдена", taskId);
            return;
        }
        Task currentTask = mayBeTask.get();
        while (true) {
            System.out.println("введите поле для изменения, далее через пробел новое значение (пример:da 21.06.2033):  t - заголовок, d - описание, i - идентификатор пользователя,\n" +
                    " s- статус, da - дата завершения, e - завершить редактирование");

            String input = consoleHandler.readLine();
            String fieldDescription = input.split(" ")[0];
            String pattern = "t|d|i|s|da|e";

            if (!fieldDescription.matches(pattern)) {
                System.out.println("Введен некорректный идентификатор поля");
                continue;
            }
            if (fieldDescription.equalsIgnoreCase("E")) {
                System.out.println(" Редактирование завершено.");
                setTasksForUsers();
                return;
            }

            String newValueForField = StringUtils.getValueForParameter(input, " ");
            switch (fieldDescription) {
                case "t" -> TaskUtils.setTaskTitle(currentTask, newValueForField);
                case "d" -> TaskUtils.setTaskDescription(currentTask, newValueForField);
                case "i" -> TaskUtils.setTasksUserId(currentTask, newValueForField);
                case "s" -> TaskUtils.setTaskStatus(currentTask, newValueForField);
                case "da" -> TaskUtils.setTaskToDate(currentTask, newValueForField);
            }

        }


    }


    private void addNewTask() {
        Task task = new Task();
        Long id = tasksRepository.generateTaskId();
        task.setId(id);
        task.setStatus(TaskStatus.NEW);
        while (!TaskUtils.isTaskValid(task)) {
            System.out.println("введите поле для заполнения, далее через пробел новое значение (пример:da 21.06.2033):  t - заголовок, d - описание, i - идентификатор пользователя,\n" +
                    " da - дата завершения, e - завершить редактирование");

            String input = consoleHandler.readLine();
            String fieldDescription = input.split(" ")[0];
            String pattern = "t|d|i|da|e";

            if (!fieldDescription.matches(pattern)) {
                System.out.println("Введен некорректный идентификатор поля");
                continue;
            }
            if (fieldDescription.toUpperCase().startsWith("E")) {
                System.out.println(" Создание новой задачи прервано пользователем.");
                return;
            }

            String newValueForField = StringUtils.getValueForParameter(input, " ");
            switch (fieldDescription) {
                case "t" -> TaskUtils.setTaskTitle(task, newValueForField);
                case "d" -> TaskUtils.setTaskDescription(task, newValueForField);
                case "i" -> TaskUtils.setTasksUserId(task, newValueForField);
                case "da" -> TaskUtils.setTaskToDate(task, newValueForField);
            }

        }
        tasksRepository.saveTask(task);
        setTasksForUsers();
        System.out.println(String.format("Новое задание успешно добавлено " +
                "для пользователя с id = %d", task.getUserId()));
    }

    private void deleteTask(Long taskId) {
        tasksRepository.deleteTask(taskId);
        setTasksForUsers();

    }

    private void changeTaskStatus(Long taskId) {
        Optional<Task> mayBeTask = tasksRepository.findTaskById(taskId);
        if (mayBeTask.isEmpty()) {
            String.format(" Задача с id = %d не найдена", taskId);
            return;
        }
        Task currentTask = mayBeTask.get();
        while (true) {
            System.out.println("введите новый статус задачи: w - в работе, n - новая, d - выполнено ");
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
                System.out.println(String.format(" Статус задачи с id = %d изменен", taskId));
                return;
            }
        }
    }

    private void shutdownApp() {
        System.out.println("Для подтверждения выхода введите 'Y', для отмены операции любую букву");
        String input = consoleHandler.readLine();
        if (input.equalsIgnoreCase("Y")) {
            System.out.println("Приложение корректно завершило работу");
            System.exit(0);
        }
    }

    private void readTasksForUser(Long userId) {
        Optional<User> mayBeUser = usersRepository.findUserById(userId);
        if (mayBeUser.isPresent()) {
            mayBeUser.get().getTasks().stream()
                    .forEach(System.out::println);
        } else {
            System.out.println(String.format(" Пользоваетль с id = %d не найден", userId));
        }
    }


    private void setTasksForUsers() {
        List<User> users = usersRepository.getUsers();
        Set<Task> tasks = tasksRepository.getTasks();
        for (User user : users) {
            user.getTasks().clear();
            for (Task task : tasks) {
                if (task.getUserId() == user.getId()) {
                    user.getTasks().add(task);
                }
            }
        }
    }
}
