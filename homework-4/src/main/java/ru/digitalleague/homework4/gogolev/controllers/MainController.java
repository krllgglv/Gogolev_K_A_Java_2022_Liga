package ru.digitalleague.homework4.gogolev.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.digitalleague.homework4.gogolev.services.MainService;

import java.util.Map;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;
    @GetMapping("/{commandName}")
    public ResponseEntity<?> handleCommand(@PathVariable String commandName,
                                           @RequestParam(required = false) Map<String, String> reqParams){

        return mainService.handleCommand(commandName, reqParams);
    }
}
