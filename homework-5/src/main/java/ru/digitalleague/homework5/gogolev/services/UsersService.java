package ru.digitalleague.homework5.gogolev.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.digitalleague.homework5.gogolev.dto.InternalTaskDto;
import ru.digitalleague.homework5.gogolev.dto.UserDto;
import ru.digitalleague.homework5.gogolev.entities.user.User;
import ru.digitalleague.homework5.gogolev.exceptions.NoSuchUserException;
import ru.digitalleague.homework5.gogolev.repository.UsersRepository;
import ru.digitalleague.homework5.gogolev.util.TaskUtils;
import ru.digitalleague.homework5.gogolev.util.UserUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final TasksService tasksService;


    public UserDto findById(Long id) {
        Optional<User> mayBeUser = usersRepository.findUserById(id);
        if (mayBeUser.isEmpty()) {
            log.error("No user with id =" + id);
            throw new NoSuchUserException("No user with id =" + id);
        }
        return UserUtils.entityToDto(mayBeUser.get());
    }

    @PostConstruct
    private void init() {
        setTasksForUsers();
    }

    public void setTasksForUsers() {
        List<User> users = usersRepository.getUsers();
        Set<InternalTaskDto> tasks = tasksService.findAll();
        for (User user : users) {
            user.getTasks().clear();
            for (InternalTaskDto task : tasks) {
                if (task.getUserId().equals(user.getId())) {
                    user.getTasks().add(TaskUtils.internalDtoToEntity(task));
                }
            }
        }
    }

    public void clearState() {
        usersRepository.clearState();
    }
}



