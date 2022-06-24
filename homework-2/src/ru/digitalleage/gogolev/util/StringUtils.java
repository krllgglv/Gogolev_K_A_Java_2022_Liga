package ru.digitalleage.gogolev.util;

public class StringUtils {

    private StringUtils(){};
    public static Long getIdFromString(String input){
        Long id = -1L;
        String[] splited = input.split(" ");
        for (String s : splited) {
            if (s.startsWith("-id")){
                return Long.parseLong(s.split("=")[1]);
            }
        }
        return id;
    }
}
