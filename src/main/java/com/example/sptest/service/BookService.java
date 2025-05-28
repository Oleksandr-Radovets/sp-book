package com.example.sptest.service;

import com.example.sptest.dto.BookDto;
import com.example.sptest.dto.CreateBookDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookDto saveBook(CreateBookDto createBookDto);

    List<BookDto> allBook(Pageable pageable);

    BookDto getById(Long id);
}
