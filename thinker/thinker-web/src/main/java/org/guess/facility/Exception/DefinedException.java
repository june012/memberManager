package org.guess.facility.Exception;

/**
 * Created by wan.peng on 2016/10/14.
 */
public class DefinedException extends RuntimeException {
    private String errorCode;

    public DefinedException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public DefinedException(String errorCode, String message, Throwable e) {
        super(message, e);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

}
