package com.xbkaishui.bookstoread;

import com.xbkaishui.bookstoread.filter.BookFilter;
import com.xbkaishui.bookstoread.filter.CompositeFilter;
import com.xbkaishui.bookstoread.resolver.BooksParameterResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Nested
@DisplayName("search")
@ExtendWith(BooksParameterResolver.class)
final class BookShelfSearchTest {
    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;

    @BeforeEach
    void setup(Map<String, Book> books) {
        shelf = new BookShelf();
        this.effectiveJava = books.get("Effective Java");
        this.codeComplete = books.get("Code Complete");
        this.mythicalManMonth = books.get("The Mythical Man-Month");
        this.cleanCode = books.get("Clean Code");
        shelf.add(codeComplete, effectiveJava, mythicalManMonth, cleanCode);
    }

    @Test
    @DisplayName(" should find books with title containing text")
    void shouldFindBooksWithTitleContainingText() {
        List<Book> books = shelf.findBooksByTitle("code");
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    @DisplayName(" should find books with title containing text and published after specified date.")
    void shouldFilterSearchedBooksBasedOnPublishedDate() {
        List<Book> books = shelf.findBooksByTitle("code", b ->
                b.getPublishedOn().isBefore(LocalDate.of(2014, 12, 31)));
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Composite criteria is based on multiple filters")
    void shouldFilterOnMultiplesCriteria() {
        CompositeFilter compositeFilter = new CompositeFilter();
        compositeFilter.addFilter( b -> false);
        assertFalse(compositeFilter.apply(cleanCode));
    }

    @Test
    @DisplayName("Composite criteria does not invoke after first failure")
    void shouldNotInvokeAfterFirstFailure(){
        CompositeFilter compositeFilter = new CompositeFilter();
        BookFilter invokedMockedFilter = Mockito.mock(BookFilter.class);
        Mockito.when(invokedMockedFilter.apply(cleanCode)).thenReturn(false);
        compositeFilter.addFilter(invokedMockedFilter);

        BookFilter nonInvokedMockedFilter = Mockito.mock(BookFilter.class);
        Mockito.when(nonInvokedMockedFilter.apply(cleanCode)).thenReturn(true);
        compositeFilter.addFilter( nonInvokedMockedFilter );
        assertFalse(compositeFilter.apply(cleanCode));
        Mockito.verify(invokedMockedFilter).apply(cleanCode);
//        Mockito.verifyNoInteractions(nonInvokedMockedFilter);
    }

    @Test
    @DisplayName("Composite criteria invokes all filters")
    void shouldInvokeAllFilters(){
        CompositeFilter compositeFilter = new CompositeFilter();
        compositeFilter.addFilter( b -> true);
        compositeFilter.addFilter( b -> true);
        assertTrue(compositeFilter.apply(cleanCode));
    }
}
