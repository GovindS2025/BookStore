package com.org.bookstore_backend.controller;

import com.org.bookstore_backend.entity.Book;
import com.org.bookstore_backend.repo.BookRepo;
import com.org.bookstore_backend.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    //here what will to, we will try to autowired the BookService so that we can call all the methods inside it.

    @Autowired
    private BookService bookService;
    @GetMapping("/all")
    public List <Book> getBooks() {

    //now we will getMapping the Book and which is coming from the bookService.
   return bookService.getBooks();

   //similarly, I am going to create all the mappings
}
// so to check in postman, you need to put full @requestMapping path+postMapping

@PostMapping("/insert")
    //here we are passing the mapping as insert.
    public Book insert(@RequestBody Book book){
    return bookService.addBook(book);
}
@PutMapping("/update/{id}")
    public Book update(@PathVariable String id,@RequestBody Book book){

        return bookService.updateBook(id,book);
}

@DeleteMapping("/delete/{id}")
    public Book delete(@PathVariable String id){
        return bookService.deleteBook(id);
}
}