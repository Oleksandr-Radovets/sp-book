package com.example.sptest.repository;

import com.example.sptest.dto.BookDto;
import com.example.sptest.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("from Book b where b.price between ?1 and ?2")
    List<Book> findAllByPriceBetween(BigDecimal low, BigDecimal high);
}
