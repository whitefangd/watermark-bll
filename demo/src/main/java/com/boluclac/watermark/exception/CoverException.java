package com.boluclac.watermark.exception;

public class CoverException extends BaseException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void print() {
        System.out.println("No find setting of Cover File or Cover File setting is wrong!");
    }

}
