package com.example.sptest.mapper;

import com.example.sptest.dto.BookDto;
import com.example.sptest.dto.CreateBookDto;
import com.example.sptest.model.Book;

public interface BookMapper {

    BookDto toDto(Book book);

    Book toModel(CreateBookDto createBookDto);

}
