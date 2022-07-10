package ru.digitalleague.gogolev.util;

import org.junit.jupiter.api.Test;
import ru.digitalleague.gogolev.entities.user.User;

import static org.assertj.core.api.Assertions.assertThat;

class UserUtilsTest {

    @Test
    void createUserFromMetadata() {
        String[] metadata = {"1", "User"};

        User userFromMetadata = UserUtils.createUserFromMetadata(metadata);
        assertThat(userFromMetadata.getId())
                .isEqualTo(1L);
        assertThat(userFromMetadata.getName())
                .isEqualTo("User");
    }
}