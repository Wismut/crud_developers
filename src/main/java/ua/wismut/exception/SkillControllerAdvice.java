package ua.wismut.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.wismut.security.JwtAuthenticationException;

@RestControllerAdvice
public class SkillControllerAdvice {
    @ResponseBody
    @ExceptionHandler({SkillNotFoundException.class, EmptyResultDataAccessException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    ResponseEntity<Object> skillNotFoundExceptionHandler(RuntimeException ex) {
        return new ResponseEntity<>(new JsonResponse(ex.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase()),
                HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    ResponseEntity<Object> skillNumberFormatExceptionHandler(NumberFormatException ex) {
        return new ResponseEntity<>(new JsonResponse("Could not found skill",
                HttpStatus.NOT_FOUND.getReasonPhrase()),
                HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> illegalArgumentExceptionHandler(RuntimeException ex) {
        return new ResponseEntity<>(new JsonResponse(ex.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(JwtAuthenticationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> jwtAuthenticationExceptionHandler(JwtAuthenticationException ex) {
        return new ResponseEntity<>(new JsonResponse(ex.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()),
                HttpStatus.BAD_REQUEST);
    }
}
