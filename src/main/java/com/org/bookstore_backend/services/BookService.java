package com.org.bookstore_backend.services;

import com.org.bookstore_backend.DTO.BookDTO;
import com.org.bookstore_backend.DTO.BookSaveDTO;
import com.org.bookstore_backend.DTO.BookUpdateDTO;
import com.org.bookstore_backend.entity.Book;

import java.util.List;

public interface BookService {


    Book saveBook(Book book);
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book updateBook(Long id, Book book);
    void deleteBook(Long id);

    Book getBookById(String id);

    Book updateBook(String id, Book updatedBook);

    void deleteBook(String id);

    List<Book> getBooks();

    Book addBook(Book book);

    List<Book> getBooksByTitle(String title);

    List<Book> getBooksByAuthor(String author);

    List<Book> getBooksByGenre(String genre);

    List<Book> getBooksByPriceRange(double minPrice, double maxPrice);

    List<Book> getBooksByPublicationDate(String publicationDate);

    String updateBook(BookUpdateDTO bookUpdateDTO);

    List<BookDTO> getAllBook();

   String  deleteBook(int id);

    String addBook(BookSaveDTO bookSaveDTO);
}
