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
class AlterCommandFactoryTest {


    @Mock
    private UsersService usersService;
    @Mock
    private TasksService tasksService;
    @InjectMocks
    private AlterCommandFactory alterCommandFactory;

    @BeforeEach
    private void setArgumentsPattern() {
        alterCommandFactory.setAlterCommandParamsPattern("i|t|d|u|s|da");
    }

    @Test
    void shouldThrowInvalidCommandParametersExceptionIfInputContainsNoParameters() {

        String wrongInput = "alter_";
        assertThatThrownBy(() -> alterCommandFactory.create(wrongInput))
                .isInstanceOf(InvalidCommandParametersException.class)
                .hasMessageContaining("No parameters provided");
    }

    @Test
    void shouldThrowInvalidCommandParametersExceptionIfInputContainsTooManyParameters() {

        String wrongInput = "alter_i=1?t=tit1?d=dis1?u=1?da=11.11.1111?s=w?dd=123";
        assertThatThrownBy(() -> alterCommandFactory.create(wrongInput))
                .isInstanceOf(InvalidCommandParametersException.class)
                .hasMessageContaining("To many parameters");
    }

    @Test
    void shouldThrowInvalidCommandParametersExceptionIfInputContainsUnknownParameter() {

        String wrongInput = "alter_i=1?tit1=tit?d=dis1?a=a?da=11.11.1111";
        assertThatThrownBy(() -> alterCommandFactory.create(wrongInput))
                .isInstanceOf(InvalidCommandParametersException.class)
                .hasMessageContaining("Unknown");
    }

    @Test
    void shouldThrowInvalidCommandParameterValueExceptionIfInputDoesNotContainValueForParameter() {

        String wrongInput = "alter_i=1?t=tit1?d=dis1?a=?da=11.11.1111";
        assertThatThrownBy(() -> alterCommandFactory.create(wrongInput))
                .isInstanceOf(InvalidCommandParameterValueException.class)
                .hasMessageContaining("No value");
    }

    @Test
    void shouldThrowInvalidCommandParameterValueExceptionIfIdIsNegative() {

        String wrongInput = "alter_i=1?t=tit1?d=dis1?u=-100?da=11.11.1111";
        assertThatThrownBy(() -> alterCommandFactory.create(wrongInput))
                .isInstanceOf(InvalidCommandParameterValueException.class)
                .hasMessageContaining("positive");
    }

    @Test
    void shouldThrowInvalidCommandParameterExceptionIfIdOfTaskIsNotProvided() {

        String wrongInput = "alter_t=tit1?d=dis1?u=100?da=11.11.1111";
        assertThatThrownBy(() -> alterCommandFactory.create(wrongInput))
                .isInstanceOf(InvalidCommandParametersException.class)
                .hasMessageContaining("!!Id of task is absent!!");
    }

    @Test
    void shouldThrowInvalidCommandParameterValueExceptionIfProvidedIncorrectStatus() {

        String wrongInput = "alter_i=1?t=tit1?d=dis1?s=tt?da=11.11.1111";
        assertThatThrownBy(() -> alterCommandFactory.create(wrongInput))
                .isInstanceOf(InvalidCommandParameterValueException.class)
                .hasMessageContaining("Unknown value for status parameter");
    }

    @Test
    void shouldReturnCorrectMapIfInputParametersAreValid() {
        String correctInput = "alter_i=1?t=tit1?d=dis1";
        assertThat(alterCommandFactory.validateParameters(correctInput))
                .hasSize(3)
                .containsKeys("i", "t", "d")
                .containsValues("1", "tit1", "dis1");

    }


}