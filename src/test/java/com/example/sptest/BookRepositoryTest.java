package com.example.sptest;

import com.example.sptest.model.Book;
import com.example.sptest.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book cheapBook;
    private Book midBook;
    private Book expensiveBook;
    private Book deletedBook;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();

        cheapBook = new Book();
        cheapBook.setTitle("Cheap Book");
        cheapBook.setAuthor("Author A");
        cheapBook.setIsbn("ISBN-1");
        cheapBook.setPrice(new BigDecimal("10.00"));
        cheapBook.setDeleted(false);

        midBook = new Book();
        midBook.setTitle("Mid Book");
        midBook.setAuthor("Author B");
        midBook.setIsbn("ISBN-2");
        midBook.setPrice(new BigDecimal("50.00"));
        midBook.setDeleted(false);

        expensiveBook = new Book();
        expensiveBook.setTitle("Expensive Book");
        expensiveBook.setAuthor("Author C");
        expensiveBook.setIsbn("ISBN-3");
        expensiveBook.setPrice(new BigDecimal("100.00"));
        expensiveBook.setDeleted(false);

        deletedBook = new Book();
        deletedBook.setTitle("Deleted Book");
        deletedBook.setAuthor("Author D");
        deletedBook.setIsbn("ISBN-4");
        deletedBook.setPrice(new BigDecimal("50.00"));
        deletedBook.setDeleted(true);

        bookRepository.saveAll(List.of(cheapBook, midBook, expensiveBook, deletedBook));
    }

    @Test
    @DisplayName("findAllByPriceBetween should return only non-deleted books within price range")
    void whenPriceBetween20And80_thenReturnOnlyMidBook() {
        List<Book> result = bookRepository.findAllByPriceBetween(
                new BigDecimal("20.00"),
                new BigDecimal("80.00")
        );

        assertThat(result)
                .hasSize(1)
                .containsExactly(midBook);
    }

    @Test
    @DisplayName("findAllByPriceBetween should not include soft-deleted books")
    void whenSoftDeletedInRange_thenExcludeDeleted() {
        List<Book> result = bookRepository.findAllByPriceBetween(
                new BigDecimal("40.00"),
                new BigDecimal("60.00")
        );

        assertThat(result)
                .doesNotContain(deletedBook)
                .contains(midBook);
    }

    @Test
    @DisplayName("findAllByPriceBetween inclusive boundaries and ignore deleted")
    void whenRangeIncludesExactPrices_thenIncludeActiveAtBounds() {
        List<Book> result = bookRepository.findAllByPriceBetween(
                new BigDecimal("10.00"),
                new BigDecimal("100.00")
        );

        assertThat(result)
                .hasSize(3)
                .containsExactlyInAnyOrder(cheapBook, midBook, expensiveBook);
    }
}
