package com.project;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.project.domain.Book;

@Configuration
public class LibraryApplicationDataConfiguration {

    @Bean
    public List<Book> books() {
        final List<Book> books = new ArrayList<>();
        books.add(new Book(1, "book_name1", "author_name1", new BigDecimal("2000")));
        books.add(new Book(2, "book_name2", "author_name2", new BigDecimal("3000")));
        books.add(new Book(3, "book_name3", "author_name3", new BigDecimal("4000")));
        books.add(new Book(4, "book_name4", "author_name4", new BigDecimal("5000")));
        return books;
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }
}
