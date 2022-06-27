package ru.digitalleage.gogolev.util;

public class StringUtils {

    private StringUtils() {
    }


    public static Long getIdFromString(String input) {
        Long id = -1L;
        String[] splited = input.split(" ");
        for (String s : splited) {
            if (s.startsWith("-id")) {
                try {
                    return Long.parseLong(getValueForParameter(s, "="));
                } catch (NumberFormatException e) {
                    System.out.println("������������� ������ ���� ����� ������");
                }
            }
        }
        return id;
    }

    public static String getValueForParameter(String input, String delimeter) {
        String value = "";
        try {
            value = input.split(delimeter)[1];
        } catch (Exception e) {
            System.out.println(" ����������� ������ ��������. ������� ������� help ��� ������������ � �����������");
        }
        return value;
    }
}
