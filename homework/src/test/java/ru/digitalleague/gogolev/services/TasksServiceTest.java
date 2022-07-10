package ru.digitalleague.gogolev.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.digitalleague.gogolev.dto.InternalTaskDto;
import ru.digitalleague.gogolev.exceptions.NoSuchTaskException;
import ru.digitalleague.gogolev.repository.TasksRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TasksServiceTest {

    @Mock
    private TasksRepository tasksRepository;
    @InjectMocks
    private TasksService tasksService;

    @Test
    void shouldReturnEmptySetIfThereAreNoTasksInDatabase() {
        when(tasksRepository.findAll())
                .thenReturn(Collections.emptySet());
        Set<InternalTaskDto> result = tasksService.findAll();
        assertThat(result)
                .isEmpty();
    }

    @Test
    void shouldThrowNoSuchTaskExceptionIfThereAreNoTaskWithProvidedId() {
        Long id = -100L;
        when(tasksRepository.findTaskById(id))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> tasksService.findById(id))
                .isInstanceOf(NoSuchTaskException.class)
                .hasMessageContaining("No task");
    }

}