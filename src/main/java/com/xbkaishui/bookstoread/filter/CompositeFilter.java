package com.xbkaishui.bookstoread.filter;

import com.xbkaishui.bookstoread.Book;

import java.util.ArrayList;
import java.util.List;

public final class CompositeFilter implements BookFilter {

    private List<BookFilter> filters;

    public CompositeFilter() {
        this.filters = new ArrayList<>();
    }

    @Override
    public boolean apply(Book b) {
        return filters.stream().map(filter -> filter.apply(b)).reduce(true, (b1, b2) -> b1 && b2);
    }

    public void addFilter(BookFilter filter) {
        this.filters.add(filter);
    }
}
