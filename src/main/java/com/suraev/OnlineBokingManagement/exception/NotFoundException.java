package com.suraev.OnlineBokingManagement.exception;

import lombok.Builder;

@Builder
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
}
