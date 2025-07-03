package com.org.bookstore_backend.services;

import com.org.bookstore_backend.repo.AuthorRepo;

import java.util.List;

public interface AuthorService {
    public List<AuthorRepo> getAuthors();
    public AuthorRepo addAuthor(AuthorRepo author);

    com.org.bookstore_backend.entity.Author addAuthor(com.org.bookstore_backend.entity.Author author);

    public void deleteAuthor(String id);
    public AuthorRepo updateAuthor(String id, AuthorRepo author);

    com.org.bookstore_backend.entity.Author updateAuthor(String id, com.org.bookstore_backend.entity.Author updatedAuthor);

    public AuthorRepo getAuthorById(String id);
    public List<AuthorRepo> getAuthorsByName(String name);
    public List<AuthorRepo> getAuthorsByBookTitle(String bookTitle);
    public List<AuthorRepo> getAuthorsByGenre(String genre);

    AuthorRepo saveAuthor(AuthorRepo author);

    com.org.bookstore_backend.entity.Author saveAuthor(com.org.bookstore_backend.entity.Author author);

    List<AuthorRepo> getAllAuthors();
}
