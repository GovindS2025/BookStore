package com.org.bookstore_backend.services.Impl;

import com.org.bookstore_backend.DTO.BookDTO;
import com.org.bookstore_backend.DTO.BookSaveDTO;
import com.org.bookstore_backend.DTO.BookUpdateDTO;
import com.org.bookstore_backend.entity.Author;
import com.org.bookstore_backend.entity.Book;
import com.org.bookstore_backend.entity.Publisher;
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
    public String saveBook(BookSaveDTO dto) {
      //Author author = authorRepo.findById(dto.getAuthor_id())
       //         .orElseThrow(() -> new RuntimeException("Author not found"));

       // Publisher publisher = publisherRepo.findById(dto.getPublisher_id())
         //       .orElseThrow(() -> new RuntimeException("Publisher not found"));

        Book book = new Book();
        Author author = new Author();
        book.setTitle(dto.getTitle());

        author.setAuthorid(dto.getAuthor().getAuthorid());
        author.setName(dto.getAuthor().getName());
        book.setAuthor(author);
       // book.setPublisher(publisher);
        book.setIsbn(dto.getIsbn());
        book.setPublicationYear(dto.getPublicationYear());
        book.setPrice(dto.getPrice());

        bookRepo.save(book);
        return book.getTitle()+" added successfully";
    }

    @Override
    public List<BookDTO> getAllBook() {
        List<Book> books = bookRepo.findAll();
        List<BookDTO> bookDTOList = new ArrayList<>();

        for (Book book : books) {
            BookDTO dto = new BookDTO();
            dto.setId(book.getBookid());
            dto.setTitle(book.getTitle());
           // dto.setAuthor(book.getAuthor() != null ? book.getAuthor().getName() : "Unknown");
           // dto.setPublisher(book.getPublisher() != null ? book.getPublisher().getName() : "Unknown");
            dto.setIsbn(book.getIsbn());
            dto.setPublicationYear(book.getPublicationYear());
            dto.setPrice(book.getPrice());
            dto.setBorrowed(book.getBorrows() != null && !book.getBorrows().isEmpty());
            bookDTOList.add(dto);
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
    public String updateBook(BookUpdateDTO dto) {
       // Long bookId = dto.getBookid();
       // if (bookRepo.existsById(bookId)) {
         //   Book book = bookRepo.findById(bookId).orElse(null);
//            if (book != null) {
//                book.setTitle(dto.getTitle());
//                bookRepo.save(book);
//                return book.getTitle();
//            }

        return "Book ID Not Found";
    }

    @Override
    public String deleteBook(int id) {
//        Long bookId = (long) id;
//        if (bookRepo.existsById(bookId)) {
//            bookRepo.deleteById(bookId);
//            return "Deleted Successfully";
//        }
        return "Book ID Not Found";
    }
}