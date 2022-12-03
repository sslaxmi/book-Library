package com.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.domain.Book;
import com.project.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	/**
	 * API to fetch all books in library
	 * 
	 * @return
	 */
	@GetMapping("/all")
	public ResponseEntity<?> getAllBooks() {

		Optional<List<Book>> books = bookService.getBooks();

		return books.isPresent() ? ResponseEntity.ok(books.get()) : ResponseEntity.notFound().build();
	}

	/**
	 * api to fetch book by id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getBook(@PathVariable int id) {
		Optional<Book> books = bookService.getBook(id);
		return books.isPresent() ? ResponseEntity.ok(books.get()) : ResponseEntity.notFound().build();
	}

	/**
	 * api to create/add new book to library
	 * 
	 * @param book
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> storeBook(@RequestBody Book book) {
		if (!isValid(book)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		bookService.storeBook(book);
		return ResponseEntity.ok().build();
	}

	/**
	 * api to update an existing book in library
	 * 
	 * @param book
	 * @return
	 */
	@PutMapping
	public ResponseEntity<?> updateBook(@RequestBody Book book) {
		if (!isValid(book)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		bookService.updateBook(book);
		return ResponseEntity.ok().build();
	}

	/**
	 * api to delete/remove a book from library
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable int id) {
		bookService.deleteBook(id);
		return ResponseEntity.ok().build();
	}

	private boolean isValid(Book book) {
		return !book.getName().isEmpty() && !book.getAuthor().isEmpty() && book.getPrice() != null;
	}
}
