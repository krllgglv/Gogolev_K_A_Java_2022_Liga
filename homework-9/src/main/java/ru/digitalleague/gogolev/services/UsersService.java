package ru.digitalleague.gogolev.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.digitalleague.gogolev.dto.UserDto;
import ru.digitalleague.gogolev.entities.task.Task;
import ru.digitalleague.gogolev.entities.user.User;
import ru.digitalleague.gogolev.exceptions.NoSuchUserException;
import ru.digitalleague.gogolev.repository.UsersRepository;
import ru.digitalleague.gogolev.util.UserUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final FileSystemProvider fileSystemProvider;
    @Value("${files.save.users}")
    private String pathToSaveTasksState;


    public User findById(Long id) {
        Optional<User> mayBeUser = usersRepository.findById(id);
        if (mayBeUser.isEmpty()) {
            logNoSuchUserExc("No user with id =" + id);
        }
        return mayBeUser.get();
    }


    public void clearState() {
        usersRepository.deleteAll();
    }

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    public void saveStateToFile() {
        List<User> tasks = findAll();
        List<String> content = tasks.stream()
                .map(UserUtils::convertUserToCSVString)
                .toList();
        fileSystemProvider.saveStateToFile(pathToSaveTasksState, content);
    }

    public UserDto findUserWithMaxQuantityOfTasks(Specification<Task> tasksSpec) {
        Optional<User> mayBeUser = usersRepository.findUserWithMaxQuantityOfTasks(tasksSpec);
        if (mayBeUser.isEmpty()) {
            logNoSuchUserExc("No users with specified parameters found");
        }
        return UserUtils.entityToDto(mayBeUser.get());
    }

    private void logNoSuchUserExc(String message) {
        log.error(message);
        throw new NoSuchUserException(message);
    }
}



