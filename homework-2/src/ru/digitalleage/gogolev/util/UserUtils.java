package ru.digitalleage.gogolev.util;

import ru.digitalleage.gogolev.entities.User;

public class UserUtils {
    private UserUtils(){}

    public static User createUserFromMetadata(String[] metadata) {
        var user = new User();
        user.setId(Long.parseLong(metadata[0]));
        user.setName(metadata[1]);
        return user;
    }
}
