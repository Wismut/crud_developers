package ua.wismut.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class SkillNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler({SkillNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    ResponseEntity<Object> skillNotFoundExceptionHandler(RuntimeException ex) {
        return new ResponseEntity<>(new JsonResponse(ex.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase()),
                HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler({NumberFormatException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    ResponseEntity<Object> skillNumberFormatExceptionHandler(NumberFormatException ex) {
        return new ResponseEntity<>(new JsonResponse("Could not found skill",
                HttpStatus.NOT_FOUND.getReasonPhrase()),
                HttpStatus.NOT_FOUND);
    }
}
