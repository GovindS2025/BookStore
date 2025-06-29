package com.org.bookstore_backend.services.impl;

import com.org.bookstore_backend.entity.Book;
import com.org.bookstore_backend.repo.BookRepo;
import com.org.bookstore_backend.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepo;
    //Steam API Implementation

    @Override
    public List<Book> getBooks() {
        return bookRepo.findAll();
    /*
    public List<Book> getBooks() {
        return bookRepo.findAll().stream()
                .filter(book -> book.getPrice() > 1000)
                .sorted(Comparator.comparing(Book::getTitle))
                .toList();
        //here you want to return the book >£1000


     */
    }

    @Override
    public Book addBook(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public Book deleteBook(String id) {
        Optional<Book> bookOpt = bookRepo.findById(String.valueOf(id));
        if (bookOpt.isPresent()) {
            bookRepo.deleteById(String.valueOf(id));
            return bookOpt.get();
        } else {
            return null;
        }
    }

    @Override
    public Book updateBook(String id, Book updatedBook) {
        Optional<Book> bookOpt = bookRepo.findById(String.valueOf(id));
        if (bookOpt.isPresent()) {
            Book existingBook = bookOpt.get();
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setPrice(updatedBook.getPrice());
            return bookRepo.save(existingBook);
        } else {
            return null;
        }
    }
}
