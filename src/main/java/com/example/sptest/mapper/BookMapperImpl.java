package com.example.sptest.mapper;

import com.example.sptest.dto.BookDto;
import com.example.sptest.dto.CreateBookDto;
import com.example.sptest.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements BookMapper {
    @Override
    public BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setPrice(book.getPrice());
        dto.setDescription(book.getDescription());
        dto.setCoverImage(book.getCoverImage());
        return dto;

    }

    @Override
    public Book toModel(CreateBookDto createBookDto) {
        Book book = new Book();
        book.setTitle(createBookDto.getTitle());
        book.setAuthor(createBookDto.getAuthor());
        book.setIsbn(createBookDto.getIsbn());
        book.setPrice(createBookDto.getPrice());
        book.setDescription(createBookDto.getDescription());
        book.setCoverImage(createBookDto.getCoverImage());
        return book;
    }
}
