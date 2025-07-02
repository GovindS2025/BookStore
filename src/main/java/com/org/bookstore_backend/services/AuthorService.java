package com.org.bookstore_backend.services;

import com.org.bookstore_backend.repo.Author;

import java.util.List;

public interface AuthorService {
    public List<Author> getAuthors();
    public Author addAuthor(Author author);
    public  Author deleteAuthor(String id);
    public Author updateAuthor(String id, Author author);
    public Author getAuthorById(String id);
    public List<Author> getAuthorsByName(String name);
    public List<Author> getAuthorsByBookTitle(String bookTitle);
    public List<Author> getAuthorsByGenre(String genre);

}
