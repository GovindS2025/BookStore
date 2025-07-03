package com.org.bookstore_backend.services.impl;

import com.org.bookstore_backend.entity.Publisher;
import com.org.bookstore_backend.repo.PublisherRepository;
import com.org.bookstore_backend.services.PublisherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public Publisher getPublisherById(String id) {
        return publisherRepository.findById(id).orElse(null);
    }

    @Override
    public Publisher savePublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    public Publisher updatePublisher(String id, Publisher publisher) {
        publisher.setId(id);
        return publisherRepository.save(publisher);
    }

    @Override
    public void deletePublisher(String id) {
        publisherRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Publisher addBooksToPublisher(String publisherId, List<String> bookIds) {
        Publisher publisher = getPublisherById(publisherId);
        if (publisher != null) {
            publisher.getBookIds().addAll(bookIds.stream()
                    .filter(bookId -> !publisher.getBookIds().contains(bookId))
                    .toList());
            return publisherRepository.save(publisher);
        }
        return null;
    }
}
