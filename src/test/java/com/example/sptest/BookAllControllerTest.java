package com.example.sptest;

import com.example.sptest.dto.BookDto;
import com.example.sptest.dto.CreateBookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookAllControllerTest {

    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void BeforeAll(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @BeforeEach
    void setUp() throws Exception {
        CreateBookDto bookDto1 = new CreateBookDto();
        bookDto1.setAuthor("John Doe");
        bookDto1.setTitle("Book Title");
        bookDto1.setIsbn("14");
        bookDto1.setDescription("Book Description");
        bookDto1.setPrice(BigDecimal.valueOf(23));
        bookDto1.setCoverImage("book.jpg");

        CreateBookDto bookDto2 = new CreateBookDto();
        bookDto2.setAuthor("Jim");
        bookDto2.setTitle("Book Title1");
        bookDto2.setIsbn("115");
        bookDto2.setDescription("Book Description1");
        bookDto2.setPrice(BigDecimal.valueOf(10));
        bookDto2.setCoverImage("booking.jpg");

        mockMvc.perform(post("/book/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto1)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/book/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto2)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Get All books")
    void getAll_Given_Books_ShouldReturnAllBooks() throws Exception {
        MvcResult result = mockMvc.perform(get("/book/allBook")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        BookDto[] actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(), BookDto[].class);
        Assertions.assertEquals(2, actual.length);

    }

}
