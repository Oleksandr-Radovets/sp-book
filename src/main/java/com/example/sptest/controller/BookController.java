package com.example.sptest.controller;

import com.example.sptest.dto.BookDto;
import com.example.sptest.dto.CreateBookDto;
import com.example.sptest.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/save")
    public BookDto saveBook(@RequestBody CreateBookDto createBookDto){
        return bookService.saveBook(createBookDto);
    }

    @GetMapping("/allBook")
    public List<BookDto> allBook(Pageable pageable){
        return bookService.allBook(pageable);
    }


    @GetMapping("/getById")
    public BookDto getBookById(@RequestParam Long id){
     return bookService.getById(id);
    }

}
