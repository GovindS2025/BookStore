package com.org.bookstore_backend.services;

import com.org.bookstore_backend.DTO.BorrowDTO;
import com.org.bookstore_backend.DTO.BorrowSaveDTO;
import com.org.bookstore_backend.DTO.BorrowUpdateDTO;

import java.util.List;

public interface BorrowService {
    String addBorrow(BorrowSaveDTO borrowSaveDTO);

    List<BorrowDTO> getAllBorrow();

    String updateBorrow(BorrowUpdateDTO borrowUpdateDTO);
}