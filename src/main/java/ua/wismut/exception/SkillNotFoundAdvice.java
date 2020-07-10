package ua.wismut.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class SkillNotFoundAdvice extends ResponseEntityExceptionHandler {
    @ResponseBody
    @ExceptionHandler(SkillNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    ResponseEntity<Object> skillNotFoundHandler(SkillNotFoundException ex) {
        return new ResponseEntity<>(new JsonResponse(ex.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase()),
                HttpStatus.NOT_FOUND);
    }
}