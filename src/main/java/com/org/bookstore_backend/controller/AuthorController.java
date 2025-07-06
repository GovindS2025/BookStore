package com.org.bookstore_backend.controller;
import com.org.bookstore_backend.DTO.AuthorDTO;
import com.org.bookstore_backend.DTO.AuthorSaveDTO;
import com.org.bookstore_backend.DTO.AuthorUpdateDTO;
import com.org.bookstore_backend.entity.Author;
import com.org.bookstore_backend.repo.AuthorRepo;
import com.org.bookstore_backend.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/api/authors")
//@CrossOrigin(origins = "*")
//@RequiredArgsConstructor

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/author")
public class AuthorController {


    @Autowired
    private AuthorService authorService;

    @PostMapping(path = "/save")
    public String saveAuthor(@RequestBody AuthorSaveDTO authorSaveDTO)
    {
        String authorname = authorService.addAuthor(authorSaveDTO);
        return  "Added Successfully";
    }

    @GetMapping(path = "/getAllAuthor")
    public List<AuthorDTO> getAllAuthor()
    {
        return authorService.getAllAuthor();
    }

    @PutMapping(path = "/update")
    public String updateAuthor(@RequestBody AuthorUpdateDTO authorUpdateDTO)
    {
        return authorService.updateAuthor(authorUpdateDTO);
    }
    @DeleteMapping(path = "/delete/{id}")
    public String deleteAuthor(@PathVariable(value = "id") Long id)
    {
        String authorname = authorService.deleteAuthor(id);
        return  "deleteddd";
    }


}