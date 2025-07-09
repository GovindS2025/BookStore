package com.org.bookstore_backend.services.Impl;
import com.org.bookstore_backend.entity.User;
import com.org.bookstore_backend.DTO.BorrowDTO;
import com.org.bookstore_backend.DTO.BorrowSaveDTO;
import com.org.bookstore_backend.DTO.BorrowUpdateDTO;
import com.org.bookstore_backend.entity.Borrow;
import com.org.bookstore_backend.repo.BookRepo;
import com.org.bookstore_backend.repo.BorrowRepo;
import com.org.bookstore_backend.repo.UserRepo;
import com.org.bookstore_backend.services.BorrowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class BorrowServiceImpl implements BorrowService {


    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BorrowRepo borrowRepo;


    @Override
    public String addBorrow(BorrowSaveDTO borrowSaveDTO) {

        Borrow borrow = new Borrow(

                bookRepo.getReferenceById(borrowSaveDTO.getBook_id()),
                (User) userRepo.getReferenceById(borrowSaveDTO.getUser_id()),
                borrowSaveDTO.getBorrowDate(),
                (LocalDate) borrowSaveDTO.getReturnDate()

        );
        borrowRepo.save(borrow);

        return null;
    }

    @Override
    public List<BorrowDTO> getAllBorrow() {

        List<Borrow> getBorrow = borrowRepo.findAll();
        List<BorrowDTO> borrowDTOList = new ArrayList<>();

        for (Borrow borrow : getBorrow) {
            BorrowDTO borrowDTO = new BorrowDTO(
//                    borrow.getId(),
//                    borrow.getBook(),
//                    (org.apache.catalina.User) borrow.getUser(),
//                    borrow.getBorrowDate(),
//                    borrow.getReturnDate()

            );
            borrowDTOList.add(borrowDTO);

        }
        return borrowDTOList;


    }

    @Override
    public String updateBorrow(BorrowUpdateDTO borrowUpdateDTO) {

        try {


            if (borrowRepo.existsById(borrowUpdateDTO.getId())) {
                Borrow borrow = borrowRepo.getReferenceById(borrowUpdateDTO.getId());
                borrow.setBook(bookRepo.getReferenceById(borrowUpdateDTO.getBook_id()));
                borrow.setUser((User) userRepo.getReferenceById(borrowUpdateDTO.getUser_id()));
                borrow.setBorrowDate(borrowUpdateDTO.getBorrowDate());
                borrow.setReturnDate(borrowUpdateDTO.getReturnDate());

                borrowRepo.save(borrow);

                return "Borrow updated successfully.";

            } else {
                System.out.println("Borrow ID Not Found");
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;


    }
}