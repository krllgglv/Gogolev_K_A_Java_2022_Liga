package ru.digitalleague.homework5.gogolev.util;

import lombok.experimental.UtilityClass;
import ru.digitalleague.homework5.gogolev.dto.UserDto;
import ru.digitalleague.homework5.gogolev.entities.user.User;

@UtilityClass
public class UserUtils {


    public static User createUserFromMetadata(String[] metadata) {
        var user = new User();
        user.setId(Long.parseLong(metadata[0]));
        user.setName(metadata[1]);
        return user;
    }

    public static UserDto entityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setTasks(user.getTasks().stream().map(TaskUtils::entityToExternalDto).toList());
        return userDto;
    }
}
