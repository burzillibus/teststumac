/*
 * Copyright (c) 2016  Aruba S.p.A.
 */
package it.arubapec.graphometric.exception;

/**
 *
 * @author Francesco Sighieri <francesco.sighieri@staff.aruba.it>
 */
public class GraphometricException extends Exception {

    private String codeError = null;

    /**
     * Default constructor
     *
     * @param msg error message
     */
    public GraphometricException(String msg) {
        super(msg);
    }

    /**
     * Constructor
     *
     * @param errorMsg error message
     * @param innerException inner exception
     */
    public GraphometricException(String errorMsg, Throwable innerException) {
        super(errorMsg, innerException);
    }

    /**
     * Constructor
     *
     * @param innerException inner exception
     */
    public GraphometricException(Throwable innerException) {
        super(innerException);
    }

    /**
     * Constructor
     *
     * @param innerException GraphometricException to throw
     */
    public GraphometricException(GraphometricException innerException) {
        super(innerException);
        this.codeError = innerException.getCodeError();
    }

    /**
     * Constructor
     *
     * @param errorMsg error message
     * @param codeError code error
     * @param innerException inner exception
     */
    public GraphometricException(String errorMsg, String codeError, Throwable innerException) {
        super(errorMsg, innerException);
        this.codeError = codeError;
    }

    /**
     * Constructor
     *
     * @param errorMsg error message
     * @param codeError code error
     */
    public GraphometricException(String errorMsg, String codeError) {
        super(errorMsg);
        this.codeError = codeError;
    }

    /**
     * Return code error
     *
     * @return a string representing the code error.
     */
    public String getCodeError() {
        return codeError;
    }
}
