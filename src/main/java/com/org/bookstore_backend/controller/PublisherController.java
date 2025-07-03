package com.org.bookstore_backend.controller;

import com.org.bookstore_backend.entity.Publisher;

import com.org.bookstore_backend.entity.Publisher;
import com.org.bookstore_backend.services.PublisherService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
@CrossOrigin("*")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    @GetMapping("/{id}")
    public Publisher getPublisherById(@PathVariable String id) {
        return publisherService.getPublisherById(id);
    }

    @PostMapping("/add")
    public Publisher addPublisher(@RequestBody Publisher publisher) {
        return publisherService.savePublisher(publisher);
    }

    @PutMapping("/{id}")
    public Publisher updatePublisher(@PathVariable String id, @RequestBody Publisher publisher) {
        return publisherService.updatePublisher(id, publisher);
    }

    @DeleteMapping("/{id}")
    public void deletePublisher(@PathVariable String id) {
        publisherService.deletePublisher(id);
    }

    @PostMapping("/{id}/books")
    public Publisher addBooksToPublisher(@PathVariable String id, @RequestBody List<String> bookIds) {
        return publisherService.addBooksToPublisher(id, bookIds);
    }
}
