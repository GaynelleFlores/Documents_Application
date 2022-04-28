package application.exceptionHandling;

import application.exceptions.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Logger logger;

    @ExceptionHandler(value
            = {EntityNotFoundException.class})
    public ResponseEntity<String> EntityNotFoundHandling(EntityNotFoundException e) {
        logger.info(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value
            = {BusinessLogicException.class})
    public ResponseEntity<String> BusinessLogicExceptionHandling(BusinessLogicException e) {
        logger.info(e.getMessage());
        return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}