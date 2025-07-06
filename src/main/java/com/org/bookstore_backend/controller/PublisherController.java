package com.org.bookstore_backend.controller;

import com.org.bookstore_backend.DTO.PublisherDTO;
import com.org.bookstore_backend.DTO.PublisherSaveDTO;
import com.org.bookstore_backend.DTO.PublisherUpdateDTO;
import com.org.bookstore_backend.entity.Publisher;

import com.org.bookstore_backend.entity.Publisher;
import com.org.bookstore_backend.services.PublisherService;

import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin("*")
@RequestMapping("api/publisher")
    public class PublisherController {

        @Autowired
        private PublisherService publisherService;

        @PostMapping(path = "/save")
        public String savePublisher(@RequestBody PublisherSaveDTO publisherSaveDTO)
        {
            String publishername = publisherService.addPublisher(publisherSaveDTO);
            return  "Added Successfully";
        }

        @GetMapping(path = "/getAllPublisher")
        public List<PublisherDTO> getAllAuthor()
        {
            List<PublisherDTO> allPublishers = publisherService.getAllPublisher();
            return allPublishers;
        }

        @PutMapping(path = "/update")
        public String updatePublisher(@RequestBody PublisherUpdateDTO publisherUpdateDTO)
        {
            String publishername = publisherService.updatePublisher(publisherUpdateDTO);
            return  publishername;
        }

        @DeleteMapping(path = "/delete/{id}")
        public String deletePublisher(@PathVariable(value = "id")int id)
        {
            String authorname = publisherService.deletePublisher(id);
            return  "deleteddd";
        }


    }