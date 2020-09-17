package com.xbkaishui.bookstoread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookShelf {

    private List<Book> books = new ArrayList<>();

    public List<Book> books(){
        return Collections.unmodifiableList(books);
    }

    public void add(Book... booksToAdd){
        books.addAll(Arrays.asList(booksToAdd));
    }

    public List<Book> arrange(){
        return arrange(Comparator.naturalOrder());
    }

    public List<Book> arrange(Comparator<Book> criteria){
        return books.stream().sorted(criteria).collect(Collectors.toList());
    }
}
