package ru.digitalleague.gogolev.util;

import lombok.experimental.UtilityClass;
import ru.digitalleague.gogolev.dto.UserDto;
import ru.digitalleague.gogolev.entities.user.User;

@UtilityClass
public class UserUtils {


    public static UserDto entityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setTasks(user.getTasks().stream().map(TaskUtils::entityToExternalDto).toList());
        return userDto;
    }

    public static String convertUserToCSVString(User user) {
        StringBuilder sb = new StringBuilder();
        sb.append(user.getId());
        sb.append(", ");
        sb.append(user.getName());
        sb.append("\n");
        return sb.toString();
    }
}
