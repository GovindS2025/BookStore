package com.org.bookstore_backend;
import com.org.bookstore_backend.entity.Book;
import com.org.bookstore_backend.repo.BookRepo;
import com.org.bookstore_backend.services.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

	@Mock
	private BookRepo bookRepo;

	@InjectMocks
	private BookServiceImpl bookService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddBook() {
		Book book = new Book("SN100", "Java Mastery", "Govind Dev", 4200.0);
		when(bookRepo.save(book)).thenReturn(book);

		Book result = bookService.addBook(book);

		assertEquals("Java Mastery", result.getTitle());
		assertEquals(4200.0, result.getPrice());
		verify(bookRepo, times(1)).save(book);
	}

	@Test
	void testGetBooks_priceFiltered() {
		Book b1 = new Book("SN01", "Cheap Book", "Author A", 500.0);   // filtered out
		Book b2 = new Book("SN02", "Expensive Book", "Author B", 1500.0); // included
		when(bookRepo.findAll()).thenReturn(Arrays.asList(b1, b2));

		List<Book> results = bookService.getBooks();

		assertEquals(1, results.size());
		assertEquals("Expensive Book", results.get(0).getTitle());
	}
}
