package org.td2.rattrapageprog3.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.td2.rattrapageprog3.dto.ErrorResponse;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> badRequestType(Exception e) {

        ErrorResponse r = new ErrorResponse();
        r.status = 400;
        r.message = "Paramètre invalide";

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(r);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> missingParameter(Exception e) {

        ErrorResponse r = new ErrorResponse();
        r.status = 400;
        r.message = "Paramètre manquant";

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(r);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequest(BadRequestException e) {

        ErrorResponse r = new ErrorResponse();
        r.status = 400;
        r.message = e.getMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(r);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(ResourceNotFoundException e) {

        ErrorResponse r = new ErrorResponse();
        r.status = 404;
        r.message = e.getMessage();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(r);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> internalError(Exception e) {

        ErrorResponse r = new ErrorResponse();
        r.status = 500;
        r.message = "Erreur interne du serveur";

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(r);
    }
}