package com.boluclac.steganography.exception;

import java.io.IOException;

public class EncodeException extends Exception {

    public EncodeException() {

    }

    public EncodeException(IOException e) {
        addSuppressed(e);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

}
