package com.jfsoftware.henrys;

class InvalidItemException extends RuntimeException {
    private static final long serialVersionUID = 1282542169604438101L;

    InvalidItemException(String message) {
        super(message);
    }
}