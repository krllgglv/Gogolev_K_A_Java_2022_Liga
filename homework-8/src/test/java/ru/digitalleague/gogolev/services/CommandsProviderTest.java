package ru.digitalleague.gogolev.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.digitalleague.gogolev.exceptions.NoSuchCommandException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class CommandsProviderTest {
    @InjectMocks
    private CommandsProvider commandsProvider;

    @Mock
    private ApplicationContextProvider applicationContextProvider;


    @Test
    void shouldThrowNoSuchCommandExceptionIfInputContainsUnknownCommand() {

        String input = "reed_";
        assertThatThrownBy(() -> commandsProvider.provideCommand(input))
                .isInstanceOf(NoSuchCommandException.class)
                .hasMessageContaining("No such command");
    }


}