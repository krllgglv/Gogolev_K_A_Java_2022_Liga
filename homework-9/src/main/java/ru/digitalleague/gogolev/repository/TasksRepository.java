package ru.digitalleague.gogolev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.digitalleague.gogolev.entities.task.Task;

import java.util.List;

public interface TasksRepository extends JpaRepository<Task, Long> {

    @Override
    @Query("select t from Task t join fetch  t.user u")
    List<Task> findAll();
}
