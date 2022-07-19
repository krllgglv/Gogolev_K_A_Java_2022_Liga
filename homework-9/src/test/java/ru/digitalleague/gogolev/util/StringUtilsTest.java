package ru.digitalleague.gogolev.util;

import org.junit.jupiter.api.Test;
import ru.digitalleague.gogolev.exceptions.InvalidCommandParametersException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class StringUtilsTest {

    @Test
    void shouldThrowInvalidCommandParametersExceptionIfInputContainsNoParameters() {
        String input = "read";
        assertThrows(InvalidCommandParametersException.class,
                () -> StringUtils.getParametersFromRequest(input));
    }

    @Test
    void shouldReturnMapIfInputContainsParameters() {
        String input = "add_t=tit1?d=dis1?u=1?da=11.11.1111";
        assertThat(StringUtils.getParametersFromRequest(input))
                .containsKeys("t", "u", "da", "d")
                .hasSize(4);
    }
}