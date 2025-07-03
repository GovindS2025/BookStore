package com.org.bookstore_backend.services;
import com.org.bookstore_backend.entity.Book;
import java.util.List;
import java.util.List;
import java.util.function.Function;


public interface BookService{
    Book saveBook(Book book);
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book updateBook(Long id, Book book);
    void deleteBook(Long id);
}