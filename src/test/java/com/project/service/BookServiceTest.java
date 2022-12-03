package com.project.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.project.domain.Book;

public class BookServiceTest {

	List<Book> books = new ArrayList<>();

	@InjectMocks
	BookService bookService;

	@BeforeEach
	public void init() {
		books.add(getBookObj());
		books.add(getBookObj());
		bookService = new BookService(books);

	}

	@Test
	public void testGetAllBooks() throws Exception {

		assertEquals(2, bookService.getBooks().get().size());

	}

	@Test
	public void testAddBook() throws Exception {

		List<Book> books = bookService.storeBook(getBookObj());

		assertEquals(3, books.size());

	}

	@Test
	public void testGetBookById() throws Exception {

		Optional<Book> books = bookService.getBook(1);

		assertNotNull(books.get());

	}

	@Test
	public void testUpdateBook() throws Exception {

		Book updateBook = getBookObj();
		updateBook.setName("Updated Book");

		Book books = bookService.updateBook(updateBook);

		assertEquals("Updated Book", books.getName());

	}
	
	@Test
	public void testDeleteBook() throws Exception {

		bookService.deleteBook(1);

		assertEquals(1, books.size());

	}

	private static Book getBookObj() {
		Book book = new Book();
		book.setId(1);
		book.setAuthor(randomAlphabetic(8));
		book.setName(randomAlphabetic(8));
		return book;
	}

}
