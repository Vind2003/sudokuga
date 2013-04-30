package com.raulexposito.sudokuga.exception;

public class BuilderException extends Exception {

    public BuilderException(final String msg) {
        super(msg);
    }

    public BuilderException(final String msg, final Throwable t) {
        super(msg);
        this.setStackTrace(t.getStackTrace());
    }
}
