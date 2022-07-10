package ru.digitalleague.gogolev.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.digitalleague.gogolev.dto.InternalTaskDto;
import ru.digitalleague.gogolev.entities.task.TaskStatus;
import ru.digitalleague.gogolev.entities.user.User;
import ru.digitalleague.gogolev.exceptions.NoSuchUserException;
import ru.digitalleague.gogolev.repository.UsersRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
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
        when(usersRepository.findUserById(id))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> usersService.findById(id))
                .isInstanceOf(NoSuchUserException.class)
                .hasMessageContaining("No user with id =");
    }


    @Test
    void shouldCorrectlySetTasksForUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Name 1");
        Set<InternalTaskDto> tasksFromTasksService = Set.of(
                new InternalTaskDto(1L, "tit1", "dis1", TaskStatus.NEW, 1L, LocalDate.now()),
                new InternalTaskDto(2L, "tit2", "dis2", TaskStatus.NEW, 2L, LocalDate.now()),
                new InternalTaskDto(3L, "tit3", "dis3", TaskStatus.NEW, 1L, LocalDate.now()),
                new InternalTaskDto(4L, "tit4", "dis4", TaskStatus.NEW, 1L, LocalDate.now())
        );
        List<User> usersFromUserRepository = new ArrayList<>();
        usersFromUserRepository.add(user);

        when(tasksService.findAll())
                .thenReturn(tasksFromTasksService);
        when(usersRepository.getUsers())
                .thenReturn(usersFromUserRepository);

        usersService.setTasksForUsers();

        assertThat(usersRepository.getUsers().get(0).getTasks())
                .doesNotContainNull()
                .hasSize(3);

    }
}