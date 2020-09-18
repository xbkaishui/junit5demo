package com.xbkaishui.bookstoread;

import com.xbkaishui.bookstoread.resolver.BooksParameterResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(BooksParameterResolver.class)
public class BookShelfProgressTest {
    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;
    private Book refactoring;

    @BeforeEach
    void init(Map<String, Book> books) {
        shelf = new BookShelf();
        this.effectiveJava = books.get("Effective Java");
        this.codeComplete = books.get("Code Complete");
        this.mythicalManMonth = books.get("The Mythical Man-Month");
        this.cleanCode = books.get("Clean Code");
        this.refactoring = books.get("Refactoring: Improving the Design of Existing Code");
        shelf.add(effectiveJava, cleanCode, codeComplete, mythicalManMonth, refactoring);
    }

    @Test
    @DisplayName("is 0% completed and 100% to-read when no book is read yet")
    void progress100PercentUnread() {
        Progress progress = shelf.progress();
        assertThat(progress.getCompleted()).isEqualTo(0);
        assertThat(progress.getToRead()).isEqualTo(100);
    }

    @Tag("nightly")
    @Tag("generate-progress")
    @Test
    @DisplayName("is 40% completed and 60% to-read when 2 books are finished and 3 books not read yet")
    void progressWithCompletedAndToReadPercentages() {
        effectiveJava.startedReadingOn(LocalDate.of(2016, Month.JULY, 1));
        effectiveJava.finishedReadingOn(LocalDate.of(2016, Month.JULY, 31));

        cleanCode.startedReadingOn(LocalDate.of(2016, Month.AUGUST, 1));
        cleanCode.finishedReadingOn(LocalDate.of(2016, Month.AUGUST, 31));
        Progress progress = shelf.progress();
        assertThat(progress.getCompleted()).isEqualTo(40);
        assertThat(progress.getToRead()).isEqualTo(60);
    }
}
