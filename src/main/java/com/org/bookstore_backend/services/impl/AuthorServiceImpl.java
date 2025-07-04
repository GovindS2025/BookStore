package com.org.bookstore_backend.services.impl;

import com.org.bookstore_backend.DTO.AuthorDTO;
import com.org.bookstore_backend.DTO.AuthorSaveDTO;
import com.org.bookstore_backend.DTO.AuthorUpdateDTO;
import com.org.bookstore_backend.entity.Author;
import com.org.bookstore_backend.repo.AuthorRepo;
import com.org.bookstore_backend.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceIMPL implements AuthorService {

    @Autowired
    private AuthorRepo authorRepo;


    @Override
    public String addAuthor(AuthorSaveDTO authorSaveDTO) {

        Author author = new Author(
                authorSaveDTO.getName()
        );
        AuthorRepo.save(author);
        return author.getName();

    }

    @Override
    public List<AuthorDTO> getAllAuthor() {

        List<Author> getAuthors = authorRepo.findAll();
        List<AuthorDTO> authorDTOList = new ArrayList<>();

        for(Author author : getAuthors)
        {
            AuthorDTO authorDTO = new AuthorDTO(
                    author.getAuthorid(),
                    author.getName()
            );
            authorDTOList.add(authorDTO);

        }
        return authorDTOList;
    }

    @Override
    public String updateAuthor(AuthorUpdateDTO authorUpdateDTO) {

        if (authorRepo.existsById(authorUpdateDTO.getAuthorid())) {
            Author author = authorRepo.getById(authorUpdateDTO.getAuthorid());
            author.setName(authorUpdateDTO.getName());

            authorRepo.save(author);
            return author.getName();

        } else {
            System.out.println("Author ID Not Exist!!!!!!!!");
        }
        return null;

    }

    @Override
    public String deleteAuthor(int id) {

        if(authorRepo.existsById(id))
        {
            authorRepo.deleteById(id);
        }
        else
        {
            System.out.println("ID Not Found");
        }


        return null;
    }
}