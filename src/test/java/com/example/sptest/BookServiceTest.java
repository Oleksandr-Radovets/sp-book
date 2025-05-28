package com.example.sptest;

import com.example.sptest.dto.BookDto;
import com.example.sptest.dto.CreateBookDto;
import com.example.sptest.mapper.BookMapper;
import com.example.sptest.model.Book;
import com.example.sptest.repository.BookRepository;
import com.example.sptest.service.BookService;
import com.example.sptest.service.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName(value = "Should save new book and return BookDto")
    public void saveBook_WithValidData_ShouldReturnSavedBookDto(){
        CreateBookDto createDto = new CreateBookDto();
        createDto.setAuthor("Test Author");
        createDto.setTitle("Test Title");
        createDto.setPrice(BigDecimal.valueOf(43));
        createDto.setDescription("Test Description");
        createDto.setIsbn("123");
        createDto.setCoverImage("Test Cover Image");

        Book book = new Book();
        book.setAuthor(createDto.getAuthor());
        book.setTitle(createDto.getTitle());
        book.setPrice(createDto.getPrice());
        book.setDescription(createDto.getDescription());
        book.setIsbn(createDto.getIsbn());
        book.setCoverImage(createDto.getCoverImage());

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setAuthor(book.getAuthor());
        savedBook.setTitle(book.getTitle());
        savedBook.setPrice(book.getPrice());
        savedBook.setDescription(book.getDescription());
        savedBook.setIsbn(book.getIsbn());
        savedBook.setCoverImage(book.getCoverImage());

        BookDto expectedDto = new BookDto();
        expectedDto.setId(1L);
        expectedDto.setAuthor(savedBook.getAuthor());
        expectedDto.setTitle(savedBook.getTitle());
        expectedDto.setPrice(savedBook.getPrice());
        expectedDto.setDescription(savedBook.getDescription());
        expectedDto.setIsbn(savedBook.getIsbn());
        expectedDto.setCoverImage(savedBook.getCoverImage());

        when(bookMapper.toModel(createDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.toDto(savedBook)).thenReturn(expectedDto);


        BookDto result = bookService.saveBook(createDto);


        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        assertEquals("Test Author", result.getAuthor());
        assertEquals(BigDecimal.valueOf(43), result.getPrice());

    }

    @Test
    public void findBookById_WithValidId_ShouldReturnBookDto() {
        Long bookId = 1L;
        BookDto expectedDto = new BookDto();
        expectedDto.setId(bookId);
        expectedDto.setAuthor("Test Author");
        expectedDto.setTitle("Test Title");
        expectedDto.setPrice(BigDecimal.valueOf(43));
        expectedDto.setDescription("Test Description");
        expectedDto.setIsbn("123");
        expectedDto.setCoverImage("Test Cover Image");

        Book bookEntity = new Book();
        bookEntity.setId(bookId);
        bookEntity.setAuthor(expectedDto.getAuthor());
        bookEntity.setTitle(expectedDto.getTitle());
        bookEntity.setPrice(expectedDto.getPrice());
        bookEntity.setDescription(expectedDto.getDescription());
        bookEntity.setIsbn(expectedDto.getIsbn());
        bookEntity.setCoverImage(expectedDto.getCoverImage());

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));
        when(bookMapper.toDto(bookEntity)).thenReturn(expectedDto);


        BookDto actualDto = bookService.getById(bookId);


        assertEquals(expectedDto.getId(), actualDto.getId());
        assertEquals(expectedDto.getAuthor(), actualDto.getAuthor());
        assertEquals(expectedDto.getTitle(), actualDto.getTitle());
        assertEquals(expectedDto.getPrice(), actualDto.getPrice());
        assertEquals(expectedDto.getDescription(), actualDto.getDescription());
        assertEquals(expectedDto.getIsbn(), actualDto.getIsbn());
        assertEquals(expectedDto.getCoverImage(), actualDto.getCoverImage());
    }

    @Test
    @DisplayName("allBook_WithPageable_ShouldReturnListOfBookDto")
    public void allBook_WithPageable_ShouldReturnListOfBookDto() {

        Pageable pageable = PageRequest.of(0, 2);

        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Title1");
        book1.setAuthor("Author1");
        book1.setPrice(BigDecimal.valueOf(10));

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Title2");
        book2.setAuthor("Author2");
        book2.setPrice(BigDecimal.valueOf(20));

        List<Book> books = List.of(book1, book2);
        Page<Book> page = new PageImpl<>(books, pageable, books.size());

        BookDto dto1 = new BookDto();
        dto1.setId(1L);
        dto1.setTitle("Title1");
        dto1.setAuthor("Author1");
        dto1.setPrice(BigDecimal.valueOf(10));

        BookDto dto2 = new BookDto();
        dto2.setId(2L);
        dto2.setTitle("Title2");
        dto2.setAuthor("Author2");
        dto2.setPrice(BigDecimal.valueOf(20));

        when(bookRepository.findAll(pageable)).thenReturn(page);
        when(bookMapper.toDto(book1)).thenReturn(dto1);
        when(bookMapper.toDto(book2)).thenReturn(dto2);

        List<BookDto> result = bookService.allBook(pageable);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));

    }

}
