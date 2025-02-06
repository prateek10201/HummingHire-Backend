package org.humminghire.backend.aop.exceptions;

public class BlobAccessException extends RuntimeException {
    public BlobAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
