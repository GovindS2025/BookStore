package com.org.bookstore_backend.services;

import com.org.bookstore_backend.repo.Author;

import java.util.List;

public interface AuthorService {
    public List<Author> getAuthors();
    public Author addAuthor(Author author);

    com.org.bookstore_backend.entity.Author addAuthor(com.org.bookstore_backend.entity.Author author);

    public void deleteAuthor(String id);
    public Author updateAuthor(String id, Author author);

    com.org.bookstore_backend.entity.Author updateAuthor(String id, com.org.bookstore_backend.entity.Author updatedAuthor);

    public Author getAuthorById(String id);
    public List<Author> getAuthorsByName(String name);
    public List<Author> getAuthorsByBookTitle(String bookTitle);
    public List<Author> getAuthorsByGenre(String genre);

    Author saveAuthor(Author author);

    com.org.bookstore_backend.entity.Author saveAuthor(com.org.bookstore_backend.entity.Author author);

    List<Author> getAllAuthors();
}
