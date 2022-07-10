package ru.digitalleague.gogolev.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.digitalleague.gogolev.entities.user.User;
import ru.digitalleague.gogolev.util.UserUtils;
import ru.digitalleague.gogolev.exceptions.CantReadFileException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UsersRepository {

    @Value("${files.init.users}")
    private String pathToUserFile;
    private  List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public Optional<User> findUserById(Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    public void clearState() {
        users.clear();
    }

    @PostConstruct
    private void initUsers() {
        try (Stream<String> stream = Files.lines(Path.of(pathToUserFile))){
            this.users =  stream
                    .map(line -> line.split(", "))
                    .map(UserUtils::createUserFromMetadata)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            log.error("Cant read file to initialize users",e);
            throw new CantReadFileException("Cant read file to initialize users",e);
        }
    }
}

