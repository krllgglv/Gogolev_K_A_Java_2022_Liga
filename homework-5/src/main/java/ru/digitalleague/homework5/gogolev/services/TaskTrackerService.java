package ru.digitalleague.homework5.gogolev.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.digitalleague.homework5.gogolev.commands.Command;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskTrackerService {
    private final CommandsProvider commandsProvider;


    public Object handleCommand(String input) {
        Command<?> command = commandsProvider.provideCommand(input);
       return command.execute();

    }



}
