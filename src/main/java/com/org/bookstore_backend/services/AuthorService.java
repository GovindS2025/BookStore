package com.org.bookstore_backend.services;

import com.org.bookstore_backend.DTO.AuthorDTO;
import com.org.bookstore_backend.DTO.AuthorSaveDTO;
import com.org.bookstore_backend.DTO.AuthorUpdateDTO;
import com.org.bookstore_backend.entity.Author;

import java.util.List;

public interface AuthorService {

    String addAuthor(AuthorSaveDTO authorSaveDTO);

    List<AuthorDTO> getAllAuthor();

    String updateAuthor(AuthorUpdateDTO authorUpdateDTO);

    String updateAuthor(AuthorUpdateDTO authorUpdateDTO);

    String deleteAuthor(int id);
}