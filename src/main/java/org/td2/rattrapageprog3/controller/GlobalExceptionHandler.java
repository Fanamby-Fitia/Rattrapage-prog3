package org.td2.rattrapageprog3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.td2.rattrapageprog3.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> badRequest(Exception e) {
        ErrorResponse r = new ErrorResponse();
        r.status = 400;
        r.message = "Paramètre invalide";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> internalError(Exception e) {
        ErrorResponse r = new ErrorResponse();
        r.status = 500;
        r.message = "Erreur interne du serveur";
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(r);
}
}
