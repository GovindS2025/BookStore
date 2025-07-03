package com.org.bookstore_backend.controller;

import com.org.bookstore_backend.entity.Author;
import com.org.bookstore_backend.repo.AuthorRepo;
import com.org.bookstore_backend.services.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "*")

public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public Collection<AuthorRepo> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable String id) {
        return (Author) authorService.getAuthorById(id);
    }

    @PostMapping("/add")
    public Author addAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }

    @PutMapping("/{id}")
    public Author updateAuthor(@PathVariable String id, @RequestBody Author author) {
        return (Author) authorService.updateAuthor(id, (AuthorRepo) author);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable String id) {
        authorService.deleteAuthor(id);
    }
}
