package com.project.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.domain.Book;
import com.project.service.BookService;

@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	BookController bookController;

	@MockBean
	BookService bookService;

	@BeforeEach
	public void init() {

	}

	@Test
	public void testGetAllBooks() throws Exception {
		List<Book> books = new ArrayList<>();
		books.add(getBook());
		books.add(getBook());

		Optional<List<Book>> b = Optional.of(books);

		when(bookService.getBooks()).thenReturn(b);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/all");

		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isNotEmpty()).andReturn();

	}

	@Test
	public void testAddBooks() throws Exception {
		Book book = getBook();
		book.setPrice(new BigDecimal(2.5));

		String inputJson = mapToJson(book);

		List<Book> books = new ArrayList<>();
		books.add(book);

		when(bookService.storeBook(book)).thenReturn(books);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/book").contentType(MediaType.APPLICATION_JSON)
				.content(inputJson);

		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

	}

	@Test
	public void testUpdateBooks() throws Exception {
		Book book = getBook();
		book.setPrice(new BigDecimal(2.5));

		String inputJson = mapToJson(book);

		List<Book> books = new ArrayList<>();
		books.add(book);

		when(bookService.updateBook(book)).thenReturn(book);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/book").contentType(MediaType.APPLICATION_JSON)
				.content(inputJson);

		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

	}

	@Test
	public void testDeleteBooks() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/book/1")
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

	}
	
	@Test
	public void testAddInvaildBooks() throws Exception {

		Book book = getBook();
		book.setName(Strings.EMPTY);;

		String inputJson = mapToJson(book);

		List<Book> books = new ArrayList<>();
		books.add(book);

		when(bookService.updateBook(book)).thenReturn(book);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/book").contentType(MediaType.APPLICATION_JSON)
				.content(inputJson);

		mockMvc.perform(requestBuilder).andExpect(status().is5xxServerError());

	}

	private Book getBook() {
		Book book = new Book();
		book.setId(1);
		book.setAuthor(randomAlphabetic(8));
		book.setName(randomAlphabetic(8));
		return book;
	}

	private String mapToJson(Book book) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(book);
	}
}
