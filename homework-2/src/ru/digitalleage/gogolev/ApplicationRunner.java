package ru.digitalleage.gogolev;

import ru.digitalleage.gogolev.repository.TasksRepository;
import ru.digitalleage.gogolev.repository.UsersRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class ApplicationRunner {
    public static void main(String[] args)  {

        new Application(new TasksRepository(), new UsersRepository()).run();
    }

}
