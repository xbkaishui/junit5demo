package com.xbkaishui.bookstoread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BookShelf {

    private List<String> books = new ArrayList<>();

    public List<String> books(){
        return Collections.unmodifiableList(books);
    }

    public void add(String... booksToAdd){
        //left
        Arrays.stream(booksToAdd).forEach(books::add);
    }
}
