package com.inditex.msprices.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class PriceException extends Exception {
    private HttpStatus status;
    private ErrorMessage error;
}
