package ru.digitalleague.gogolev.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.digitalleague.gogolev.exceptions.NoSuchTaskException;
import ru.digitalleague.gogolev.repository.TasksRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TasksServiceTest {

    @Mock
    private TasksRepository tasksRepository;
    @InjectMocks
    private TasksService tasksService;


    @Test
    void shouldThrowNoSuchTaskExceptionIfThereAreNoTaskWithProvidedId() {
        Long id = -100L;
        when(tasksRepository.findById(id))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> tasksService.findById(id))
                .isInstanceOf(NoSuchTaskException.class)
                .hasMessageContaining("No task");
    }

}