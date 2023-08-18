package com.example.librarymanagementsystem.Controllers;

import com.example.librarymanagementsystem.Models.LibraryCard;
import com.example.librarymanagementsystem.Services.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
@Slf4j
public class CardController {
    @Autowired
    private CardService cardService;
    @PostMapping("/add")
    public String addCard(@RequestBody LibraryCard libraryCard){
        try{
            return cardService.addCard(libraryCard);
        }catch(Exception e){
            log.error("card is not added into db {} ",e.getMessage());
            return e.getMessage();
        }
    }

    @PutMapping("/cardAssign")
    public ResponseEntity<String> associate(@RequestParam("rollNo")Integer rollNo,@RequestParam("cardNo")Integer cardNo){
        try{
            String result=cardService.associate(rollNo,cardNo);
            return new ResponseEntity<String>(result,HttpStatus.ACCEPTED);
        }catch(Exception e){
            log.error("student could not be associated {} ",e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
