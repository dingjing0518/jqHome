package com.by.exception;

/**
 * Created by yagamai on 16-3-9.
 */
public class ShopNotExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ShopNotExistException() {
        super("shop not exist");
    }
}
