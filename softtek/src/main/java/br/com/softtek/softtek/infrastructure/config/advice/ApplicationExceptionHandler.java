package br.com.softtek.softtek.infrastructure.config.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArguments(MethodArgumentNotValidException error) {
        Map<String, String> mapError = new HashMap<String, String>();
        List<FieldError> field = error.getBindingResult().getFieldErrors();

        for (FieldError fieldError : field) {
            mapError.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return mapError;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, String> handleDataIntegrityViolation(DataIntegrityViolationException error) {
        Map<String, String> mapError = new HashMap<>();
        mapError.put("erro", "Usuário já cadastrado!");
        return mapError;
    }
}
