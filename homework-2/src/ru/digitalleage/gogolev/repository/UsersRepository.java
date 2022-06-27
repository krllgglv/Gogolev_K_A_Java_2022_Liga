package ru.digitalleage.gogolev.repository;

import ru.digitalleage.gogolev.entities.Task;
import ru.digitalleage.gogolev.entities.User;
import ru.digitalleage.gogolev.util.TaskUtils;
import ru.digitalleage.gogolev.util.UserUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;

public class UsersRepository {

    private static final String PATH_TO_USER_FILE = "resources/users.csv";
    private static final String SAVED_USERS_STATE_FILE = "resources/users_saved.csv";
    private final List<User> users;

    public UsersRepository() {
        this.users = initUsers();
    }


    public List<User> getUsers() {
        return users;
    }

    public Optional<User> findUserById(Long id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
    }


    private List<User> initUsers() {
        List<User> users;
        try {
            return Files.lines(Path.of(PATH_TO_USER_FILE))
                    .map(line -> line.split(", "))
                    .map(UserUtils::createUserFromMetadata)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearState() {
        users.clear();
    }

}

