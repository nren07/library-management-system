package com.example.librarymanagementsystem.Controllers;

import com.example.librarymanagementsystem.Models.Author;
import com.example.librarymanagementsystem.Services.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
@Slf4j
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping("/add")
    public ResponseEntity addAuthor(@RequestBody Author author){
        try{
            String result= authorService.addAuthor(author);
            return new ResponseEntity(result,HttpStatus.CREATED);
        }catch(Exception e){
            log.error("author could not be added in db {}",e.getMessage());
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
