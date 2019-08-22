package com.boluclac.watermark.exception;

public class SecretException extends BaseException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void print() {
        System.out.println("No find setting of Secret File or Secret File setting is wrong!");
    }

}
