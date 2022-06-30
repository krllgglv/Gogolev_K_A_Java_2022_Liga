package ru.digitalleague.homework4.gogolev.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.digitalleague.homework4.gogolev.entities.user.User;
import ru.digitalleague.homework4.gogolev.exceptions.CantReadFileException;
import ru.digitalleague.homework4.gogolev.util.UserUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Repository
@RequiredArgsConstructor
public class UsersRepository {

    @Value("${files.init.users}")
    private String pathToUserFile;

    @Value("${files.save.users}")
    private String pathToSaveUsersState;
    private  List<User> users;


    public List<User> getUsers() {
        return users;
    }

    public Optional<User> findUserById(Long id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
    }

    public void clearState() {
        users.clear();
    }

    @PostConstruct
    private void initUsers() {
        try {
            this.users =  Files.lines(Path.of(pathToUserFile))
                    .map(line -> line.split(", "))
                    .map(UserUtils::createUserFromMetadata)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            log.error("Cant read file to initialize users",e);
            throw new CantReadFileException("Cant read file to initialize users",e);
        }
    }
}

