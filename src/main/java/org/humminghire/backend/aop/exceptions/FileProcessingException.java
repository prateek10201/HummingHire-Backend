package org.humminghire.backend.aop.exceptions;

import java.io.IOException;

public class FileProcessingException extends RuntimeException {
    public FileProcessingException(String message, IOException e) {
        super(message);
    }
}
