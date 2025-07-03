package com.org.bookstore_backend.services.impl;

import com.org.bookstore_backend.entity.Book;
import com.org.bookstore_backend.repo.BookRepo;
import com.org.bookstore_backend.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    @Autowired
    public BookServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    @Override
    public Book getBookById(String id) {
        return bookRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    @Override
    public Book updateBook(String id, Book updatedBook) {
        return bookRepo.findById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setPrice(updatedBook.getPrice());
            book.setGenre(updatedBook.getGenre());
            book.setPublicationDate(updatedBook.getPublicationDate());
            return bookRepo.save(book);
        }).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    @Override
    public void deleteBook(String id) {
        if (!bookRepo.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepo.deleteById(id);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepo.findAll();
    }

    @Override
    public Book addBook(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookRepo.findAll().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .sorted(Comparator.comparing(Book::getTitle))
                .toList();
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookRepo.findAll().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .sorted(Comparator.comparing(Book::getAuthor))
                .toList();
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        return bookRepo.findAll().stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .sorted(Comparator.comparing(Book::getGenre))
                .toList();
    }

    @Override
    public List<Book> getBooksByPriceRange(double minPrice, double maxPrice) {
        return bookRepo.findAll().stream()
                .filter(book -> book.getPrice() >= minPrice && book.getPrice() <= maxPrice)
                .sorted(Comparator.comparing(Book::getPrice))
                .toList();
    }

    @Override
    public List<Book> getBooksByPublicationDate(String publicationDate) {
        return bookRepo.findAll().stream()
                .filter(book -> book.getPublicationDate().equalsIgnoreCase(publicationDate))
                .sorted(Comparator.comparing(Book::getPublicationDate))
                .toList();
    }
}
