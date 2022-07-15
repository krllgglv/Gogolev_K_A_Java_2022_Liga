package ru.digitalleague.gogolev.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.digitalleague.gogolev.dto.InternalTaskDto;
import ru.digitalleague.gogolev.entities.task.TaskStatus;
import ru.digitalleague.gogolev.exceptions.NoSuchUserException;
import ru.digitalleague.gogolev.repository.UsersRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock

    private UsersRepository usersRepository;
    @Mock
    private TasksService tasksService;

    @InjectMocks
    private UsersService usersService;

    @Test
    void shouldThrowNoSuchUserExceptionIfThereAreNoUserWithProvidedId() {
        Long id = -100L;
        when(usersRepository.findById(id))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> usersService.findById(id))
                .isInstanceOf(NoSuchUserException.class)
                .hasMessageContaining("No user with id =");
    }

}