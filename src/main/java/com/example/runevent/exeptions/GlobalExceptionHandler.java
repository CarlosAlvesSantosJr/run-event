package com.example.runevent.exeptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiExeption> handleRuntime(RuntimeException ex) {
        // Mensagens de "não encontrado" podem ser tratadas diferentemente, aqui usamos BAD_REQUEST
        ApiExeption err = new ApiExeption(ex.getMessage(), List.of());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExeption> handleAll(Exception ex) {
        ApiExeption err = new ApiExeption("Erro interno: " + ex.getMessage(), List.of());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> details = ex.getBindingResult().getFieldErrors()
                .stream().map(e -> e.getField() + ": " + e.getDefaultMessage()).collect(Collectors.toList());
        ApiExeption err = new ApiExeption("Validação falhou", details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
