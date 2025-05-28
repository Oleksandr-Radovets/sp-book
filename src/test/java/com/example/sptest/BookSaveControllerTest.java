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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookSaveControllerTest {

    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void BeforeAll(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @DisplayName("Create a new book")
    void create_Valid_bookRequestDto_Success() throws Exception {
        CreateBookDto createBookDto = new CreateBookDto();
        createBookDto.setAuthor("John Doe");
        createBookDto.setTitle("Book Title");
        createBookDto.setIsbn("14");
        createBookDto.setDescription("Book Description");
        createBookDto.setPrice(BigDecimal.valueOf(23));
        createBookDto.setCoverImage("book.jpg");

        BookDto expected = new BookDto();
        expected.setAuthor(createBookDto.getAuthor());
        expected.setTitle(createBookDto.getTitle());
        expected.setIsbn(createBookDto.getIsbn());
        expected.setDescription(createBookDto.getDescription());
        expected.setPrice(createBookDto.getPrice());
        expected.setCoverImage(createBookDto.getCoverImage());

        String jsonRequest = objectMapper.writeValueAsString(createBookDto);

        MvcResult result = mockMvc.perform(post("/book/save")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andReturn();

        BookDto actual = objectMapper
                .readValue(result
                        .getResponse()
                        .getContentAsString(), BookDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(createBookDto.getAuthor(), actual.getAuthor());
        Assertions.assertEquals(createBookDto.getPrice(), actual.getPrice());
    }

}
