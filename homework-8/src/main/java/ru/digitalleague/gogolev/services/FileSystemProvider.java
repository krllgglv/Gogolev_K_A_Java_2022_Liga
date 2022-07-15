package ru.digitalleague.gogolev.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.digitalleague.gogolev.exceptions.CantSaveFileException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Slf4j
@Component
public class FileSystemProvider {

    public void saveStateToFile(String pathToFile, List<String> content) {
        try {
            Path path = Path.of(pathToFile);
            File file = path.toFile();
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            for (String line : content) {
                Files.writeString(path, line, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            log.error("Cant save state of tasks to file", e);
            throw new CantSaveFileException("Cant save state of tasks to file", e);
        }
    }
}
