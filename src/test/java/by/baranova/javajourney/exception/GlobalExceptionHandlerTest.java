package by.baranova.javajourney.exception;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleHttpClientErrorException() {
        // Arrange
        HttpClientErrorException ex = new HttpClientErrorException(HttpStatus.BAD_REQUEST);

        // Act
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleHttpClientErrorException(ex, null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("400 Bad Request", responseEntity.getBody());
    }

    @Test
    void handleMethodArgumentNotValidException() {
        // Arrange
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(new FieldError("objectName", "fieldName", "error message")));
        when(ex.getBindingResult()).thenReturn(bindingResult);

        // Act
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleMethodArgumentNotValidException(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("error message; ", responseEntity.getBody());
    }

    @Test
    void handleMethodNotSupportedException() {
        // Arrange
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("message");

        // Act
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleMethodNotSupportedException(ex, null);

        // Assert
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, responseEntity.getStatusCode());
        assertEquals("405 Method Not Allowed", responseEntity.getBody());
    }

    @Test
    void handleRuntimeException() {
        // Arrange
        RuntimeException ex = new RuntimeException("message");

        // Act
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleRuntimeException(ex, null);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("500 Internal Server Error", responseEntity.getBody());
    }
}