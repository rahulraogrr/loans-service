package com.ing.exceptions.custom;

import java.time.LocalDateTime;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
