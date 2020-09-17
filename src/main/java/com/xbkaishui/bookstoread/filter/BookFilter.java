package com.xbkaishui.bookstoread.filter;

import com.xbkaishui.bookstoread.Book;

@FunctionalInterface
public interface BookFilter {
    boolean apply(Book b);
}
