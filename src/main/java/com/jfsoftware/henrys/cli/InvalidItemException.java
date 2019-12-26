package com.jfsoftware.henrys.cli;

public class InvalidItemException extends RuntimeException {
    private static final long serialVersionUID = 1282542169604438101L;

    public InvalidItemException(String message) {
        super(message);
    }
}