package dev.yawkar.auxilium.service;

public class ParserUtils {

    public static boolean isCommand(String text) {
        return text.length() > 0 && text.charAt(0) == '/';
    }

    public static String parseCommand(String text) {
        return text.split("\\s")[0].substring(1);
    }
}
