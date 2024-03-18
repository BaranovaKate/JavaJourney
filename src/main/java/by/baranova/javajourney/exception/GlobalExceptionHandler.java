package by.baranova.javajourney.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

/**
 * Global exception handler class to handle exceptions thrown by controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /** Logger for logging error messages. */
    static final Logger LOGGER = LogManager
            .getLogger(GlobalExceptionHandler.class);


    /**
     * Handles HTTP client error exceptions.
     *
     * @param ex the thrown HTTP client error exception
     * @param request the web request
     * @return a response entity with a status code and error message
     */
    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<Object> handleHttpClientErrorException(
           final HttpClientErrorException ex, final WebRequest request) {
        LOGGER.error("400 Bad Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("400 Bad Request");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            errorMessage.append(fieldError.getDefaultMessage()).append("; ");
        }
        LOGGER.error("400 Bad Request: {}", errorMessage.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMessage.toString());
    }

    /**
     * Handles HTTP request method not supported exceptions.
     *
     * @param ex the thrown HTTP request method not supported exception
     * @param request the web request
     * @return a response entity with a status code and error message
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Object> handleMethodNotSupportedException(
            final HttpRequestMethodNotSupportedException ex,
           final WebRequest request) {
        LOGGER.error("405 Method Not Allowed");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("405 Method Not Allowed");
    }


    /**
     * Handles runtime exceptions.
     *
     * @param ex the thrown runtime exception
     * @param request the web request
     * @return a response entity with a status code and error message
     */
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(
            final RuntimeException ex, final WebRequest request) {
        LOGGER.error("500 Internal Server Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("500 Internal Server Error");
    }
}
