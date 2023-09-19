package com.maveric.restaurantmanagementsystem.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class AppExceptionTest {

    @InjectMocks
    private AppExceptionHandler appExceptionHandler;

    @Mock
    private ProductNotFoundException productNotFoundException;

    @Mock
    private InvalidStatusException invalidStatusException;

    @Mock
    private ItemOutOfStockException itemOutOfStockException;

    @Mock
    private NoOrderException noOrderException;

    @Mock
    private ServiceException serviceException;

    @Mock
    private UserNotFoundException userNotFoundException;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private EmptyItemException emptyItemException;

    @Mock
    private NullPointerException nullPointerException;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProductNotFoundException() {
        String exceptionMessage = "Product Not Found";
        when(productNotFoundException.getMessage()).thenReturn(exceptionMessage);
        ResponseEntity<Object> responseEntity =
                appExceptionHandler.handleProductNotFoundException(productNotFoundException);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, ((ExceptionMessage) Objects.requireNonNull(responseEntity.getBody())).getMessage());
    }

    @Test
    void testInvalidStatusException() {
        String exceptionMessage = "Status must be either A, C or X";
        when(invalidStatusException.getMessage()).thenReturn(exceptionMessage);
        ResponseEntity<Object> responseEntity =
                appExceptionHandler.handleInvalidStatusException(invalidStatusException);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, ((ExceptionMessage) Objects.requireNonNull(responseEntity.getBody())).getMessage());
    }

    @Test
    void testUserNotFoundException() {
        String exceptionMessage = "User Not Found";
        when(userNotFoundException.getMessage()).thenReturn(exceptionMessage);
        ResponseEntity<Object> responseEntity =
                appExceptionHandler.handleUserNotFoundException(userNotFoundException);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, ((ExceptionMessage) Objects.requireNonNull(responseEntity.getBody())).getMessage());
    }

    @Test
    void testItemOutOfStockException() {
        String exceptionMessage = "Product Out of stock";
        when(itemOutOfStockException.getMessage()).thenReturn(exceptionMessage);
        ResponseEntity<Object> responseEntity =
                appExceptionHandler.handleItemOutOfStockException(itemOutOfStockException);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, ((ExceptionMessage) Objects.requireNonNull(responseEntity.getBody())).getMessage());
    }

    @Test
    void testNoOrderException() {
        String exceptionMessage = "There are no orders";
        when(noOrderException.getMessage()).thenReturn(exceptionMessage);
        ResponseEntity<Object> responseEntity =
                appExceptionHandler.handleNoOrderException(noOrderException);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, ((ExceptionMessage) Objects.requireNonNull(responseEntity.getBody())).getMessage());
    }

    @Test
    void testServiceException() {
        String exceptionMessage = "Unable to create order";
        when(serviceException.getMessage()).thenReturn(exceptionMessage);
        ResponseEntity<Object> responseEntity =
                appExceptionHandler.handleServiceException(serviceException);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, ((ExceptionMessage) Objects.requireNonNull(responseEntity.getBody())).getMessage());
    }

    @Test
    void testHandleInvalidArgument() {
        FieldError error1 = new FieldError("objectName1", "productName", "Product Name is required");
        FieldError error2 = new FieldError("objectName2", "description", "Description is required");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(error1, error2));
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        Map<String, String> errorMap = appExceptionHandler.handleInvalidArgument(methodArgumentNotValidException);

        assertEquals(2, errorMap.size());
        assertEquals("Product Name is required", errorMap.get("productName"));
        assertEquals("Description is required", errorMap.get("description"));
    }

    @Test
    void testHandleNullPointerException() {
        String exceptionMessage = "NullPointerException message";
        when(nullPointerException.getMessage()).thenReturn(exceptionMessage);
        ResponseEntity<Object> responseEntity = appExceptionHandler.handleNullPointerException(nullPointerException);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, ((ExceptionMessage) Objects.requireNonNull(responseEntity.getBody())).getMessage());
    }

    @Test
    void testHandleEmptyItemException() {
        String exceptionMessage = "Empty Item Exception";
        when(emptyItemException.getMessage()).thenReturn(exceptionMessage);
        ResponseEntity<Object> responseEntity = appExceptionHandler.handleItemNotAddedException(emptyItemException);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, ((ExceptionMessage) Objects.requireNonNull(responseEntity.getBody())).getMessage());
    }
}
