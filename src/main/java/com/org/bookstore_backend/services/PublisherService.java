package com.org.bookstore_backend.services;

import com.org.bookstore_backend.DTO.PublisherDTO;
import com.org.bookstore_backend.DTO.PublisherSaveDTO;
import com.org.bookstore_backend.DTO.PublisherUpdateDTO;
import com.org.bookstore_backend.entity.Publisher;

import java.util.List;

public interface PublisherService {
    List<Publisher> getAllPublishers();
    Publisher getPublisherById(String id);
    Publisher savePublisher(Publisher publisher);
    Publisher updatePublisher(String id, Publisher publisher);

    String deletePublisher(String id);
    Publisher addBooksToPublisher(String publisherId, List<String> bookIds);

    String addPublisher(PublisherSaveDTO publisherSaveDTO);

    List<PublisherDTO> getAllPublisher();

    String updatePublisher(PublisherUpdateDTO publisherUpdateDTO);

    String deletePublisher(int id);
}
