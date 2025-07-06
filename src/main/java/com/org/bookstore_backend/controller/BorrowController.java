package com.org.bookstore_backend.controller;

import com.org.bookstore_backend.DTO.BorrowDTO;
import com.org.bookstore_backend.DTO.BorrowSaveDTO;
import com.org.bookstore_backend.DTO.BorrowUpdateDTO;
import com.org.bookstore_backend.services.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping(path = "/save")
    public String saveBorrow(@RequestBody BorrowSaveDTO borrowSaveDTO)
    {
        String borrowBooks = borrowService.addBorrow(borrowSaveDTO);
        return  "Borrow Successfully";
    }

    @GetMapping(path = "/getAllBorrow")
    public List<BorrowDTO> getAllBorrow()
    {
        return borrowService.getAllBorrow();
    }

    @PutMapping(path = "/update")
    public String updateBorrow(@RequestBody BorrowUpdateDTO borrowUpdateDTO)
    {
        String borrow = borrowService.updateBorrow(borrowUpdateDTO);
        return  "Updated";
    }

}