package ru.digitalleague.gogolev.commands.factories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.digitalleague.gogolev.exceptions.InvalidCommandParameterValueException;
import ru.digitalleague.gogolev.exceptions.InvalidCommandParametersException;
import ru.digitalleague.gogolev.services.TasksService;
import ru.digitalleague.gogolev.services.UsersService;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class DeleteCommandFactoryTest {

    @Mock
    private UsersService usersService;
    @Mock
    private TasksService tasksService;
    @InjectMocks
    private DeleteCommandFactory deleteCommandFactory;

    @Test
    void shouldThrowInvalidCommandParametersExceptionIfInputContainsNoParameters() {

        String wrongInput = "delete_";
        assertThatThrownBy(() -> deleteCommandFactory.create(wrongInput))
                .isInstanceOf(InvalidCommandParametersException.class)
                .hasMessageContaining("No parameters provided");
    }

    @Test
    void shouldThrowInvalidCommandParametersExceptionIfIdParameterIsAbsent() {

        String wrongInput = "delete_a=1";
        assertThatThrownBy(() -> deleteCommandFactory.create(wrongInput))
                .isInstanceOf(InvalidCommandParametersException.class)
                .hasMessageContaining("Parameter i is absent");
    }

    @Test
    void shouldThrowInvalidCommandParametersValueExceptionIfIdParameterIsAbsent() {

        String wrongInput = "delete_i=-1";
        assertThatThrownBy(() -> deleteCommandFactory.create(wrongInput))
                .isInstanceOf(InvalidCommandParameterValueException.class)
                .hasMessageContaining("positive digit");
    }

    @Test
    void shouldThrowInvalidCommandParametersExceptionIfThereAreTooManyParameters() {

        String wrongInput = "delete_a=1?dd=1";
        assertThatThrownBy(() -> deleteCommandFactory.create(wrongInput))
                .isInstanceOf(InvalidCommandParametersException.class)
                .hasMessageContaining("Wrong quantity of parameters");
    }

}