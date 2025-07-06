package com.org.bookstore_backend.controller;

import com.org.bookstore_backend.DTO.BookDTO;
import com.org.bookstore_backend.DTO.BookSaveDTO;
import com.org.bookstore_backend.DTO.BookUpdateDTO;
import com.org.bookstore_backend.entity.Book;
import com.org.bookstore_backend.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")


public class BookController {
    @Autowired
    private BookService bookService;
    @PostMapping(path = "/save")
    public String saveBook(@RequestBody BookSaveDTO bookSaveDTO)
    {
        // Convert DTO to entity
        Book book = new Book();
        book.setTitle(bookSaveDTO.getTitle());
        // set other fields...
        // save book and return result
        return  "Added Successfully";
    }
    @GetMapping(path = "/getAllBook")
    public List<BookDTO> getAllBook()
    {
        List<BookDTO> allBooks = bookService.getAllBook();
        return allBooks;
    }
    @PutMapping(path = "/update")
    public String updateBook(@RequestBody BookUpdateDTO bookUpdateDTO)
    {
        String bookname = bookService.updateBook(bookUpdateDTO);
        return  bookname;
    }
    @DeleteMapping(path = "/delete/{id}")
    public String deleteBook(@PathVariable(value = "id") int id)
    {
        bookService.deleteBook((int) id);
        return  "deleteddd";
    }
}
