package ru.digitalleague.homework4.gogolev.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.digitalleague.homework4.gogolev.dto.InternalTaskDto;
import ru.digitalleague.homework4.gogolev.dto.UserDto;
import ru.digitalleague.homework4.gogolev.dto.command.CommandDto;
import ru.digitalleague.homework4.gogolev.entities.task.TaskStatus;
import ru.digitalleague.homework4.gogolev.exceptions.CantReadFileException;
import ru.digitalleague.homework4.gogolev.exceptions.NoSuchCommandException;
import ru.digitalleague.homework4.gogolev.util.StringUtils;
import ru.digitalleague.homework4.gogolev.util.TaskUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {
    @Value("${files.helpFile}")
    private String pathToHelpFile;
    private final TasksService tasksService;
    private final UsersService usersService;
    private final CommandHandlerService commandHandlerService;


    public ResponseEntity<?> handleCommand(String commandName, Map<String, String> parameters) {

        CommandDto commandDto = commandHandlerService.handleCommand(commandName, parameters);

        return switch (commandDto.getName()) {
            case "READ" -> readTasksForUser(commandDto.getArguments());
            case "ADD" -> addNewTask(commandDto.getArguments());
            case "EXIT" -> shutdownApp();
            case "DELETE" -> deleteTask(commandDto.getArguments());
            case "ALTER" -> alterTask(commandDto.getArguments());
            case "CLEAR_STATE" -> clearState();
            case "SAVE_STATE_TO_FILE" -> saveStateToFiles();
            case "HELP" -> showHelpPage();
            default ->
                    throw new NoSuchCommandException(String.format("Command with name = %s not found", commandDto.getName()));
        };
    }


    private ResponseEntity<List<String>> showHelpPage() {
        List<String> helpInfo;
        try {
            helpInfo = Files.lines(Path.of(pathToHelpFile)).collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Cant read file with help info");
            throw new CantReadFileException("Cant read file with help info", e);
        }
        return new ResponseEntity<>(helpInfo, HttpStatus.OK);
    }

    private ResponseEntity<?> saveStateToFiles() {
        tasksService.saveStateToFile();
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<?> clearState() {

        tasksService.clearState();
        usersService.clearState();
        log.info("Storage is cleared");
        return ResponseEntity.ok().body("Storage is cleared");
    }

    private ResponseEntity<?> alterTask(Map<String, String> args) {
        Long taskId = StringUtils.validateAndGetIdFromString(args.get("i"));
        InternalTaskDto taskDto = tasksService.findById(taskId);
        for (Map.Entry<String, String> entry : args.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "i" -> {
                }
                case "t" -> taskDto.setTitle(value);
                case "d" -> taskDto.setDescription(value);
                case "u" -> taskDto.setUserId(StringUtils.validateAndGetIdFromString(value));
                case "s" -> TaskUtils.setTaskStatus(taskDto, value);
                case "da" -> taskDto.setToDate(StringUtils.validateAndParseDateFromString(value));
            }
        }
        tasksService.save(taskDto);
        usersService.setTasksForUsers();
        log.info("Task altered, id=" + taskDto.getId());
        return ResponseEntity.ok().body(taskDto.getId());
    }


    private ResponseEntity<?> addNewTask(Map<String, String> params) {
        InternalTaskDto taskDto = new InternalTaskDto();
        Long id = tasksService.generateTaskId();
        taskDto.setId(id);
        taskDto.setStatus(TaskStatus.NEW);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key) {
                case "t" -> taskDto.setTitle(value);
                case "d" -> taskDto.setDescription(value);
                case "u" -> taskDto.setUserId(StringUtils.validateAndGetIdFromString(value));
                case "da" -> taskDto.setToDate(StringUtils.validateAndParseDateFromString(value));
            }
        }
        tasksService.save(taskDto);
        usersService.setTasksForUsers();
        log.info("task added, id=" + taskDto.getId());
        return ResponseEntity.ok().body(taskDto.getId());
    }

    private ResponseEntity<?> deleteTask(Map<String, String> args) {
        Long taskId = Long.parseLong(args.get("id"));
        tasksService.deleteTask(taskId);
        usersService.setTasksForUsers();
        log.info("deletet task with id =" + taskId);
        return ResponseEntity.ok().build();
    }


    private ResponseEntity<?> shutdownApp() {
        System.exit(0);
        return ResponseEntity.ok().build();
    }


    private ResponseEntity<?> readTasksForUser(Map<String, String> args) {
        Long id = Long.parseLong(args.get("id"));
        UserDto userDto = usersService.findById(id);
        return ResponseEntity.ok().body(userDto);
    }


}
