package ua.bondoleks.loyaltySystem.exception.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.bondoleks.loyaltySystem.exception.LSUserNotFoundException;
import ua.bondoleks.loyaltySystem.exception.PurchaseNotFoundException;
import ua.bondoleks.loyaltySystem.payload.response.GenericErrorResponse;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, WebRequest request, List<String> errors) {
        String path = request.getDescription(false);

        logger.error("Error occurred at {}: {}", path, errors != null ? String.join(", ", errors) : "No specific errors");

        GenericErrorResponse response = new GenericErrorResponse();
        response.setTimestamp(ZonedDateTime.now());
        response.setStatus(status.value());
        response.setError(status.getReasonPhrase());
        response.setErrorMessages(errors);
        response.setPath(path);

        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, WebRequest request, String error) {
        return buildErrorResponse(status, request, Collections.singletonList(error));
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> globalErrors = ex.getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        logger.error("Validation failed at {}: {}", request.getDescription(false), ex.getMessage());

        GenericErrorResponse response = new GenericErrorResponse();
        response.setTimestamp(ZonedDateTime.now());
        response.setStatus(status.value());
        response.setError(status.getReasonPhrase());
        response.setFieldErrors(fieldErrors);
        response.setErrorMessages(globalErrors);
        response.setPath(request.getDescription(false));

        return new ResponseEntity<>(response, headers, status);
    }

    @ExceptionHandler({
            LSUserNotFoundException.class,
            PurchaseNotFoundException.class
    })
    protected ResponseEntity<Object> handleNotFoundException(RuntimeException ex, WebRequest request) {
        logger.error("Not Found Exception: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, request, ex.getMessage());
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class
    })
    protected ResponseEntity<Object> handleBadRequestException(RuntimeException ex, WebRequest request) {
        logger.error("Bad Request Exception: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, request, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        logger.error("General Exception: {}", ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, request, ex.getMessage());
    }
}
