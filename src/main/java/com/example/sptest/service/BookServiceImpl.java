package com.example.sptest.service;

import com.example.sptest.dto.BookDto;
import com.example.sptest.dto.CreateBookDto;
import com.example.sptest.mapper.BookMapper;
import com.example.sptest.model.Book;
import com.example.sptest.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    public BookServiceImpl(BookMapper bookMapper, BookRepository bookRepository) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto saveBook(CreateBookDto createBookDto) {
        Book model = bookMapper.toModel(createBookDto);
        Book save = bookRepository.save(model);
        BookDto dto = bookMapper.toDto(save);
        return dto;
    }

    @Override
    public List<BookDto> allBook(Pageable pageable) {
        Page<Book> all = bookRepository.findAll(pageable);
        return all.stream().map(bookMapper::toDto).toList();
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("can't by id " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> allBookByPriceBetween(BigDecimal low, BigDecimal high) {
        return bookRepository.findAllByPriceBetween(low, high)
                .stream().map(bookMapper::toDto).toList();
    }

}
