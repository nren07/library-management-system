package com.example.librarymanagementsystem.Controllers;

import com.example.librarymanagementsystem.Services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
