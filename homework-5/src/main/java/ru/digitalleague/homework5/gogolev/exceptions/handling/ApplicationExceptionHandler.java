package ru.digitalleague.homework5.gogolev.exceptions.handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.digitalleague.homework5.gogolev.exceptions.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApplicationError> catchCantReadFileException(CantReadFileException e) {
        return new ResponseEntity<>(new ApplicationError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                e.getMessage(),
                LocalDateTime.now()),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ApplicationError> catchCantSaveFileException(CantSaveFileException e) {
        return new ResponseEntity<>(new ApplicationError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                e.getMessage(),
                LocalDateTime.now()),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ApplicationError> catchInvalidCommandParameterException(InvalidCommandParametersException e) {
        return new ResponseEntity<>(new ApplicationError(HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                LocalDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ApplicationError> catchInvalidCommandParameterValueExceptionException(InvalidCommandParameterValueException e) {
        return new ResponseEntity<>(new ApplicationError(HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                LocalDateTime.now()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ApplicationError> catchNoSuchCommandException(NoSuchCommandException e) {
        return new ResponseEntity<>(new ApplicationError(HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                LocalDateTime.now()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ApplicationError> catchNoSuchTaskException(NoSuchTaskException e) {
        return new ResponseEntity<>(new ApplicationError(HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                LocalDateTime.now()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ApplicationError> catchNoSuchUserException(NoSuchUserException e) {
        return new ResponseEntity<>(new ApplicationError(HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                LocalDateTime.now()),
                HttpStatus.NOT_FOUND);
    }


}
