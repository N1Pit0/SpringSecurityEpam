package com.mygym.crm.backstages.exceptions;

import com.mygym.crm.backstages.exceptions.custom.NoTraineeException;
import com.mygym.crm.backstages.exceptions.custom.NoTrainerException;
import com.mygym.crm.backstages.exceptions.custom.NoTrainingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.security.sasl.AuthenticationException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { AuthenticationException.class})
    protected ResponseEntity<Object> handleSecurityException(AuthenticationException ex, WebRequest request){
        String bodyOfResponse = "Security Exception | invalid password or userName";

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatusCode.valueOf(401), request);
    }

    @ExceptionHandler(value = { AccessDeniedException.class})
    protected ResponseEntity<Object> handleSecurityException(AccessDeniedException ex, WebRequest request){
        String bodyOfResponse = "Security Exception | You are not authorized to access this resource";

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatusCode.valueOf(403), request);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfErrorResponse = "Argument or State of resource hase a illegal value.";
        return handleExceptionInternal(ex, bodyOfErrorResponse,
                new HttpHeaders(), HttpStatusCode.valueOf(409), request);
    }

    @ExceptionHandler(value = {NoTraineeException.class})
    protected ResponseEntity<Object> handleNoTraineeException(NoTraineeException ex, WebRequest request){
        String bodyOfResponse = ex.getMessage();

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatusCode.valueOf(409), request);
    }

    @ExceptionHandler(value = {NoTrainerException.class})
    protected ResponseEntity<Object> handleNoTrainerException(NoTrainerException ex, WebRequest request){
        String bodyOfResponse = ex.getMessage();

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatusCode.valueOf(409), request);
    }

    @ExceptionHandler(value = {NoTrainingException.class})
    protected ResponseEntity<Object> handleNoTrainingException(NoTrainingException ex, WebRequest request){
        String bodyOfResponse = ex.getMessage();

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatusCode.valueOf(409), request);
    }

}
