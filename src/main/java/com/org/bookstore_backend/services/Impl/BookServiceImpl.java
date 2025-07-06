package com.org.bookstore_backend.services.Impl;

import com.org.bookstore_backend.DTO.BookDTO;
import com.org.bookstore_backend.DTO.BookSaveDTO;
import com.org.bookstore_backend.DTO.BookUpdateDTO;
import com.org.bookstore_backend.entity.Book;
import com.org.bookstore_backend.repo.AuthorRepo;
import com.org.bookstore_backend.repo.BookRepo;
import com.org.bookstore_backend.repo.PublisherRepo;
import com.org.bookstore_backend.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class BookServiceImpl implements BookService {


    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private PublisherRepo publisherRepo;

    @Autowired
    private BookRepo bookRepo;


    @Override
    public String addBook(BookSaveDTO bookSaveDTO) {

        Book book = new Book(
                bookSaveDTO.getTitle(),
                authorRepo.getById((long) bookSaveDTO.getAuthor_id()),
                publisherRepo.getById(String.valueOf(bookSaveDTO.getPublisher_id()))

        );
        bookRepo.save(book);
        return book.getTitle();

    }

    @Override
    public List<BookDTO> getAllBook() {

        List<Book> getBooks = bookRepo.findAll();
        List<BookDTO> bookDTOList = new ArrayList<>();

        for(Book book : getBooks)
        {
            BookDTO bookDTO = new BookDTO(
                    book.getBookid(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublisher()

            );
            bookDTOList.add(bookDTO);

        }
        return bookDTOList;

    }

    @Override
    public Book saveBook(Book book) {
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return List.of();
    }

    @Override
    public Book getBookById(Long id) {
        return null;
    }

    @Override
    public Book updateBook(Long id, Book book) {
        return null;
    }

    @Override
    public void deleteBook(Long id) {

    }

    @Override
    public Book getBookById(String id) {
        return null;
    }

    @Override
    public Book updateBook(String id, Book updatedBook) {
        return null;
    }

    @Override
    public void deleteBook(String id) {

    }

    @Override
    public List<Book> getBooks() {
        return List.of();
    }

    @Override
    public Book addBook(Book book) {
        return null;
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return List.of();
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return List.of();
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        return List.of();
    }

    @Override
    public List<Book> getBooksByPriceRange(double minPrice, double maxPrice) {
        return List.of();
    }

    @Override
    public List<Book> getBooksByPublicationDate(String publicationDate) {
        return List.of();
    }

    @Override
    public String updateBook(BookUpdateDTO bookUpdateDTO) {

        if(bookRepo.existsById(bookUpdateDTO.getBookid())) {
            Book book = bookRepo.getReferenceById(bookUpdateDTO.getBookid());
            book.setTitle(bookUpdateDTO.getTitle());
            bookRepo.save(book);
            return book.getTitle();
        }
        else
        {
            System.out.println("Book ID Not Found");
        }


        return null;
    }

    @Override
    public String deleteBook(int id) {

        if(bookRepo.existsById(String.valueOf(id)))
        {
            bookRepo.deleteById(String.valueOf(id));
        }
        else
        {
            System.out.println("Book ID Not Found");
        }
        return null;
    }


}