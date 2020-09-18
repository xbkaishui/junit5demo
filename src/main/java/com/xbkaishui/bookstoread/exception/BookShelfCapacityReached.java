package com.xbkaishui.bookstoread.exception;

public class BookShelfCapacityReached extends RuntimeException {

    public BookShelfCapacityReached(String message) {
        super(message);
    }
}
