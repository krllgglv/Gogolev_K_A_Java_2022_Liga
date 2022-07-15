package ru.digitalleague.gogolev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.digitalleague.gogolev.entities.user.User;

public interface UsersRepository extends JpaRepository<User, Long> {
}
