package com.org.bookstore_backend;

import com.org.bookstore_backend.entity.Book;
import com.org.bookstore_backend.repo.BookRepo;
import com.org.bookstore_backend.services.Impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Unit test class for BookServiceImpl
class BookServiceImplTest {

	@Mock
	private BookRepo bookRepo;  // Mocking the BookRepo dependency

	@InjectMocks
	private BookServiceImpl bookService; // Injecting the mock into BookServiceImpl

	@BeforeEach
	void setup() {
		// Initializes the mocks before each test runs
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddBook() {
		// Creating a sample book
		Book book = new Book("SN100", "Java Mastery", "Govind Dev", 4200.0);

		// Mocking the behavior of save method
		when(bookRepo.save(book)).thenReturn(book);

		// Calling the service method
		Book result = bookService.addBook(book);

		// Asserting the result
		assertEquals("Java Mastery", result.getTitle());
		assertEquals(4200.0, result.getPrice());

		// Verifying that save method was called once
		verify(bookRepo, times(1)).save(book);
	}

	@Test
	void testGetBooks_priceFiltered() {
		// Creating books: one with price < 1000, one >= 1000
		Book b1 = new Book("SN101", "Cheap Book", "Someone", 800.0);        // should be filtered out
		Book b2 = new Book("SN102", "Valuable Book", "Someone Else", 1500.0); // should be included

		// Mocking the behavior of findAll to return both books
		when(bookRepo.findAll()).thenReturn(Arrays.asList(b1, b2));

		// Calling the service method which filters books by price
		List<Book> results = bookService.getBooks();

		// Expecting only one book to pass the filter
		assertEquals(1, results.size());

		// Asserting the remaining book's title
		assertEquals("Valuable Book", results.get(0).getTitle());
	}
}
