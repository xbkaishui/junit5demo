package com.xbkaishui.bookstoread;

import com.xbkaishui.bookstoread.exception.BookShelfCapacityReached;
import com.xbkaishui.bookstoread.filter.BookFilter;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class BookShelf {

    private List<Book> books = new ArrayList<>();
    private Integer capacity = 10;

    public BookShelf() {
    }

    public BookShelf(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<Book> books() {
        return Collections.unmodifiableList(books);
    }

    public void add(Book... booksToAdd) throws BookShelfCapacityReached {
        Arrays.stream(booksToAdd).forEach(book -> {
            if (books.size() == capacity) {
                throw new BookShelfCapacityReached(String.format("BookShelf capacity of %d is reached. You can't add more books.", this.capacity));
            }
            books.add(book);
        });
    }

    public List<Book> arrange() {
        return arrange(Comparator.naturalOrder());
    }

    public List<Book> arrange(Comparator<Book> criteria) {
        return books.stream().sorted(criteria).collect(Collectors.toList());
    }

    public Map<Year, List<Book>> groupByPublicationYear() {
        return groupBy(book -> Year.of(book.getPublishedOn().getYear()));
    }

    public <K> Map<K, List<Book>> groupBy(Function<Book, K> fx) {
        return books().stream().collect(groupingBy(fx));
    }

    public Progress progress() {
        int booksRead = Long.valueOf(books.stream().filter(Book::isRead).count()).intValue();
        int booksToRead = books.size() - booksRead;
        int percentageCompleted = booksRead * 100 / books.size();
        int percentageToRead = booksToRead * 100 / books.size();
        return new Progress(percentageCompleted, percentageToRead, 0);
    }

    public List<Book> findBooksByTitle(String title) {
        return findBooksByTitle(title, b -> true);
    }

    public List<Book> findBooksByTitle(String title, BookFilter filter) {
        return books.stream().filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase())).filter(b -> filter.apply(b)).collect(Collectors.toList());
    }
}
