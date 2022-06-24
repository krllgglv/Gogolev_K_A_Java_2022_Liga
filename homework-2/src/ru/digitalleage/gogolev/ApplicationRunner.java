package ru.digitalleage.gogolev;

import ru.digitalleage.gogolev.storage.Storage;

public class ApplicationRunner {
    public static void main(String[] args) {
        new Application(new Storage()).run();
    }
}
