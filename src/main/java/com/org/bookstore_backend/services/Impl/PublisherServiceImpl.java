package com.org.bookstore_backend.services.Impl;

import com.org.bookstore_backend.DTO.PublisherDTO;
import com.org.bookstore_backend.DTO.PublisherSaveDTO;
import com.org.bookstore_backend.DTO.PublisherUpdateDTO;
import com.org.bookstore_backend.entity.Publisher;
import com.org.bookstore_backend.repo.PublisherRepo;
import com.org.bookstore_backend.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PublisherServiceImpl implements PublisherService {
@Autowired
private PublisherRepo publisherRepo;
    @Override
    public List<Publisher> getAllPublishers() {
        return List.of();
    }

    @Override
    public Publisher getPublisherById(String id) {
        return null;
    }

    @Override
    public Publisher savePublisher(Publisher publisher) {
        return null;
    }

    @Override
    public Publisher updatePublisher(String id, Publisher publisher) {
        return null;
    }

    @Override
    public String deletePublisher(String id) {

        return id;
    }

    @Override
    public Publisher addBooksToPublisher(String publisherId, List<String> bookIds) {
        return null;
    }

    @Override
    public String addPublisher(PublisherSaveDTO publisherSaveDTO) {
        Publisher publisher = new Publisher(
                publisherSaveDTO.getName()
        );
        publisherRepo.save(publisher);
        return publisher.getName();
    }

    @Override
    public List<PublisherDTO> getAllPublisher() {
        List<Publisher> getPublishers = publisherRepo.findAll();
        List<PublisherDTO> publisherDTOList = new ArrayList<>();
        for(Publisher publisher : getPublishers)
        {
            PublisherDTO publisherDTO = new PublisherDTO(
                    publisher.getPublisherid(),
                    publisher.getName()
            );
            publisherDTOList.add(publisherDTO);
        }
        return publisherDTOList;

    }

    @Override
    public String updatePublisher(PublisherUpdateDTO publisherUpdateDTO) {
        if (publisherRepo.existsById(String.valueOf(publisherUpdateDTO.getPublisherid())))
        {
            Publisher publisher  = publisherRepo.getReferenceById(String.valueOf(publisherUpdateDTO.getPublisherid()));
            publisher.setName(publisherUpdateDTO.getName());
            publisherRepo.save(publisher);
            return publisher.getName();
        } else {
            System.out.println("Publisher ID Not Exist!!!!!!!!");
        }
        return null;
    }

    @Override
    public String deletePublisher(int id) {
        if(publisherRepo.existsById(String.valueOf(id)))
        {
            publisherRepo.deleteById(String.valueOf(id));
        }
        else
        {
            System.out.println("ID Not Found");
        }
        return null;

    }
}
//    @Override
//    @Transactional
//    public Publisher addBooksToPublisher(String publisherId, List<String> bookIds) {
//        Publisher publisher = getPublisherById(publisherId);
//        if (publisher != null) {
//            publisher.getBookIds().addAll(bookIds.stream()
//                    .filter(bookId -> !publisher.getBookIds().contains(bookId))
//                    .toList());
//            return publisherRepo.save(publisher);
//        }
