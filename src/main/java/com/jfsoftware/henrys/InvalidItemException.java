package com.jfsoftware.henrys;

class InvalidItemException extends RuntimeException {
    InvalidItemException(String message) {
        super(message);
    }
}