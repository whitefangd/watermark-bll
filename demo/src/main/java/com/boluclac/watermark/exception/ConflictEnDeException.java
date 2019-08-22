package com.boluclac.watermark.exception;

public class ConflictEnDeException extends BaseException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void print() {
        System.out.println("Conflict encode and decode a same time");
    }

}
