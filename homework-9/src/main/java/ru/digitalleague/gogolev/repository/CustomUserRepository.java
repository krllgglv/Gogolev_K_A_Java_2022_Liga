package ru.digitalleague.gogolev.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.digitalleague.gogolev.entities.task.Task;
import ru.digitalleague.gogolev.entities.user.User;

import java.util.Optional;

public interface CustomUserRepository {
    Optional<User> findUserWithMaxQuantityOfTasks(Specification<Task> spec);

}
