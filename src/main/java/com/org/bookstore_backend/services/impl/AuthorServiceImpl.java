package com.org.bookstore_backend.services.impl;

import com.org.bookstore_backend.entity.Author;
import com.org.bookstore_backend.repo.AuthorRepo;
import com.org.bookstore_backend.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepo authorRepo;

    @Autowired
    public AuthorServiceImpl(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Override
    public List<AuthorRepo> getAuthors() {
        return authorRepo.findAll();
    }

    @Override
    public Author addAuthor(Author author) {
        return authorRepo.save(author);
    }

    @Override
    public void deleteAuthor(String id) {
        if (!authorRepo.existsById(id)) {
            throw new RuntimeException("Author not found with id: " + id);
        }
        authorRepo.deleteById(id);
    }

    @Override
    public Author updateAuthor(String id, Author updatedAuthor) {
        return authorRepo.findById(id).map(author -> {
            author.setName(updatedAuthor.getName());
            author.setBiography(updatedAuthor.getBiography());
            author.setBirthYear(updatedAuthor.getBirthYear());
            return authorRepo.save(author);
        }).orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }

    @Override
    public AuthorRepo getAuthorById(String id) {
        return authorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }

    @Override
    public List<AuthorRepo> getAuthorsByName(String name) {
        return authorRepo.findAll().stream()
                .filter(a -> a.getName().equalsIgnoreCase(name))
                .sorted(Comparator.comparing(Author::getName))
                .toList();
    }

    @Override
    public List<AuthorRepo> getAuthorsByBookTitle(String bookTitle) {
        return authorRepo.findAll().stream()
                .filter(a -> a.getBiography().toLowerCase().contains(bookTitle.toLowerCase())) // placeholder
                .toList();
    }

    @Override
    public List<AuthorRepo> getAuthorsByGenre(String genre) {
        return authorRepo.findAll().stream()
                .filter(a -> a.getBiography().toLowerCase().contains(genre.toLowerCase())) // placeholder
                .toList();
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepo.save(author);
    }

    @Override
    public List<AuthorRepo> getAllAuthors() {
        return authorRepo.findAll();
    }
}
