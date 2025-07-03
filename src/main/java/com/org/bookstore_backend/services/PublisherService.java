package com.org.bookstore_backend.services;

import com.org.bookstore_backend.entity.Publisher;

import java.util.List;

public interface PublisherService {
    List<Publisher> getAllPublishers();
    Publisher getPublisherById(String id);
    Publisher savePublisher(Publisher publisher);
    Publisher updatePublisher(String id, Publisher publisher);
    void deletePublisher(String id);
    Publisher addBooksToPublisher(String publisherId, List<String> bookIds);
}
