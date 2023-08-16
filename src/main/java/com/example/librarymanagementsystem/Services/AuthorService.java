package com.example.librarymanagementsystem.Services;

import com.example.librarymanagementsystem.Models.Author;
import com.example.librarymanagementsystem.Repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public String addAuthor(Author author) throws Exception{
        //validation is done here
        if(author.getId()!=null) throw new Exception("Author id is not to be sent as perameter");
        authorRepository.save(author);
        return "Author is added successfully";
    }
}
