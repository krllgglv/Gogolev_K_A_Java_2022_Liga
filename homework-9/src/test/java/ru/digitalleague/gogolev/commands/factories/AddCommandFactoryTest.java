package ru.digitalleague.gogolev.commands.factories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.digitalleague.gogolev.exceptions.InvalidCommandParameterValueException;
import ru.digitalleague.gogolev.exceptions.InvalidCommandParametersException;
import ru.digitalleague.gogolev.services.TasksService;
import ru.digitalleague.gogolev.services.UsersService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class AddCommandFactoryTest {

    @Mock
    private UsersService usersService;
    @Mock
    private TasksService tasksService;
    @InjectMocks
    private AddCommandFactory addCommandFactory;

    @BeforeEach
    private void setArgumentsPattern() {
        addCommandFactory.setCreateCommandParamsPattern("t|d|u|da");
    }

    @Test
    void shouldThrowInvalidCommandParametersExceptionIfInputContainsNoParameters() {

        String wrongInput = "add_";
        assertThatThrownBy(() -> addCommandFactory.create(wrongInput))
                .isInstanceOf(InvalidCommandParametersException.class)
                .hasMessageContaining("No parameters provided");
    }

    @Test
    void shouldThrowInvalidCommandParametersExceptionIfInputContainsTooManyParameters() {

        String wrongInput = "add_t=tit1?d=dis1?u=1?da=11.11.1111?dd=123";
        assertThatThrownBy(() -> addCommandFactory.create(wrongInput))
                .isInstanceOf(InvalidCommandParametersException.class)
                .hasMessageContaining("Wrong quantity of parameters");
    }

    @Test
    void shouldThrowInvalidCommandParametersExceptionIfInputContainsUnknownParameter() {

        String wrongInput = "add_t=tit1?d=dis1?a=a?da=11.11.1111";
        assertThatThrownBy(() -> addCommandFactory.create(wrongInput))
                .isInstanceOf(InvalidCommandParametersException.class)
                .hasMessageContaining("Unknown");
    }

    @Test
    void shouldThrowInvalidCommandParameterValueExceptionIfInputDoesNotContainValueForParameter() {

        String wrongInput = "add_t=tit1?d=dis1?a=?da=11.11.1111";
        assertThatThrownBy(() -> addCommandFactory.create(wrongInput))
                .isInstanceOf(InvalidCommandParameterValueException.class)
                .hasMessageContaining("No value");
    }

    @Test
    void shouldThrowInvalidCommandParameterValueExceptionIfIdIsNegative() {

        String wrongInput = "add_t=tit1?d=dis1?u=-100?da=11.11.1111";
        assertThatThrownBy(() -> addCommandFactory.create(wrongInput))
                .isInstanceOf(InvalidCommandParameterValueException.class)
                .hasMessageContaining("positive");
    }

    @Test
    void shouldReturnCorrectMapIfInputParametersAreValid() {
        String correctInput = "add_t=tit1?d=dis1?u=1?da=11.11.1111";
        assertThat(addCommandFactory.validateParameters(correctInput))
                .hasSize(4)
                .containsKeys("t", "d", "u", "da")
                .containsValues("1", "tit1", "dis1", "11.11.1111");

    }
}