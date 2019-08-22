package com.boluclac.watermark.exception;

public class OutputException extends BaseException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void print() {
        System.out.println("No find setting of Output File or Output File setting is wrong!");
    }

}
