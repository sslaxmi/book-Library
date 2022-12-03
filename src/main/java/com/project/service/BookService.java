package com.project.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.domain.Book;

@Service
public class BookService {

	private final List<Book> books;

	public BookService(List<Book> books) {
		this.books = books;
	}

	/*
	 * get al books from library inventory
	 */
	public Optional<List<Book>> getBooks() {
		if (books.size() < 1) {
			return Optional.empty();
		}
		return Optional.of(this.books);
	}

	/*
	 * add new book to library inventory
	 */
	public List<Book> storeBook(Book book) {
		books.add(new Book(book.getId(), book.getName(), book.getAuthor(), book.getPrice()));
		return books;
	}

	/*
	 * get book matching id passed
	 */
	public Optional<Book> getBook(int id) {
		List<Book> booksMatched = books.stream().filter(book -> book.getId() == id).collect(Collectors.toList());
		if (booksMatched.size() < 1) {
			return Optional.empty();
		}
		return Optional.of(booksMatched.get(0));
	}

	/*
	 * update an existing book
	 */
	public Book updateBook(Book book) {
		Optional<Book> bookMatched = books.stream().filter(b -> book.getId() == b.getId()).findFirst();
		if (bookMatched.isPresent()) {
			Book bookFound = bookMatched.get();
			books.remove(bookFound);
			bookFound.setAuthor(book.getAuthor());
			bookFound.setName(book.getName());
			bookFound.setPrice(book.getPrice());
			books.add(bookFound);
		}
		
		return book;
	}

	/*
	 * delete book by id passed
	 */
	public void deleteBook(int id) {
		Optional<Book> bookMatched = books.stream().filter(book -> book.getId() == id).findFirst();
		if (bookMatched.isPresent()) {
			books.remove(bookMatched.get());
		}
	}
}
