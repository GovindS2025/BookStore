package com.org.bookstore_backend.services;

import com.org.bookstore_backend.entity.Book;

import java.util.List;

public interface BookService  {
    public List<Book> getBooks();

    public Book addBook(Book book);

    public Book deleteBook(String id);
    // we are passing the id, so Books can be deleted through id when user hit from api.
    public Book updateBook(String id, Book book);

    //So we need to pass the reference of the value is to be updated. for that we will pass Book reference book.


}
