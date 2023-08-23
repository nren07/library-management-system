package com.example.librarymanagementsystem.Controllers;

import com.example.librarymanagementsystem.Services.TransactionService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/transaction")
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/issueBook")
    public String issueBookToStudent(@RequestParam("cardNo")int cardNo,@RequestParam("bookId")int bookId){
        try{
            return transactionService.issueBookToStudent(cardNo,bookId);
        }catch(Exception e){
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    @PostMapping("/returnBook")
    public String returnBook(@RequestParam Integer cardNo, @RequestParam Integer bookId){
        try{
            return transactionService.issueBookToStudent(cardNo,bookId);
        }catch(Exception e){
            log.error(e.getMessage());
            return e.getMessage();
        }
    }
    @GetMapping("/getFineInYear")
    public int getCollectFineInYear(@RequestParam String date){
        return transactionService.getCollectFineInYear(date);
    }
}
