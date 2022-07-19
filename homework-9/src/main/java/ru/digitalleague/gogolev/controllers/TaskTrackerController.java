package ru.digitalleague.gogolev.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.digitalleague.gogolev.services.TaskTrackerService;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class TaskTrackerController {
    private final TaskTrackerService taskTrackerService;


    @GetMapping()
    public ResponseEntity<?> handleCommand(@RequestParam String command) {
        return ResponseEntity.ok(taskTrackerService.handleCommand(command));
    }


}
