package ru.digitalleague.gogolev.commands.command;

import lombok.extern.slf4j.Slf4j;
import ru.digitalleague.gogolev.exceptions.CantReadFileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class ShowHelpCommand implements Command<List<String>> {
    private static final String PATH_TO_HELP_FILE = "src/main/resources/files/help.txt";

    @Override
    public List<String> execute() {
        List<String> helpInfo;
        try (Stream<String> stream = Files.lines(Path.of(PATH_TO_HELP_FILE))) {
            helpInfo = stream
                    .toList();
        } catch (IOException e) {
            log.error("Cant read file with help info");
            throw new CantReadFileException("Cant read file with help info", e);
        }
        return helpInfo;
    }
}
