package com.inditex.msprices.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Locale;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = {PriceException.class})
    public ResponseEntity<ErrorMessage> priceException(PriceException ex, WebRequest request) {
        return new ResponseEntity<>(ErrorMessage.builder()
                .code(ex.getError().getCode())
                .message(messageSource.getMessage(ex.getError().getKey(), null, Locale.getDefault()))
                .build(), ex.getStatus());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorMessage> generalException(Exception ex, WebRequest request) {
        ErrorMessage errorMsg = ErrorCatalog.ERROR_INTERNO.getError();
        log.error(ErrorCatalog.ERROR_INTERNO.getLogError(), ex);
        return new ResponseEntity<>(ErrorMessage.builder()
                .code(errorMsg.getCode())
                .message(messageSource.getMessage(errorMsg.getKey(), null, Locale.getDefault()))
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
