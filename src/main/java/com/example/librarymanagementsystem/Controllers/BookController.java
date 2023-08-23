package com.example.librarymanagementsystem.Controllers;

import com.example.librarymanagementsystem.BookResponseDto.BookResponseDto;
import com.example.librarymanagementsystem.Enum.Genre;
import com.example.librarymanagementsystem.RequestDto.AddBookDto;
import com.example.librarymanagementsystem.Services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public String addBook(@RequestBody AddBookDto addBookDto){
        try{
            return bookService.addBook(addBookDto);
        }catch(Exception e){
            log.error("book could not be added in Db {}",e.getMessage());
            return e.getMessage();
        }
    }

    @GetMapping("/getBookByGenre")
    public ResponseEntity getBookByGenre(@RequestParam("genre") Genre genre){
        List<BookResponseDto>bookList=bookService.getBookListByGenre(genre);
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @GetMapping("/getCntBookByGivenGenre")
    public int getCntBookByGivenGenre(@RequestBody Genre genre){
        
        return 0;
    }
}
