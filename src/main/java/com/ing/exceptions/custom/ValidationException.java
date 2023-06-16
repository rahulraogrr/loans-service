package com.ing.exceptions.custom;

import lombok.Getter;
import java.util.List;

@Getter
public class ValidationException extends RuntimeException{
    private final List<String> errors;

    public ValidationException(List<String> errors) {
        this.errors = errors;
    }
}