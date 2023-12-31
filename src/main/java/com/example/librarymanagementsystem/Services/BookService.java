package com.example.librarymanagementsystem.Services;

import com.example.librarymanagementsystem.BookResponseDto.BookResponseDto;
import com.example.librarymanagementsystem.Enum.Gender;
import com.example.librarymanagementsystem.Enum.Genre;
import com.example.librarymanagementsystem.Models.Author;
import com.example.librarymanagementsystem.Models.Book;
import com.example.librarymanagementsystem.Repositories.AuthorRepository;
import com.example.librarymanagementsystem.Repositories.BookRepository;
import com.example.librarymanagementsystem.RequestDto.AddBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public String addBook(AddBookDto request) throws Exception{
        //some validation
        Optional<Author> optionalAuthor=authorRepository.findById(request.getAuthorId());
        if(!optionalAuthor.isPresent()) throw new Exception("Author id is invalid ");
        Author author=optionalAuthor.get();
        //extract data from request and make an object of book
        Book book=new Book(request.getName(),true,request.getGenre(),request.getPublishDate(),request.getPrice());

        book.setAuthor(author);
        List<Book> list=author.getBookList();

        list.add(book);
        author.setBookList(list);

        //therefore bidirectional mapping is done here so we need to store in parent class

        authorRepository.save(author);
        return "Book is successfully added ";

    }

    public List<BookResponseDto> getBookListByGenre(Genre genre){
        List<Book>responceList=bookRepository.findBooksByGenre(genre);
        List<BookResponseDto>ansList=new ArrayList<>();
        for(Book book : responceList){
            BookResponseDto bookResponseDto=new BookResponseDto(book.getName(),book.isAvailable(),book.getGenre(),
                                                book.getPublishDate(),book.getPrice(),book.getAuthor().getName());
            ansList.add(bookResponseDto);
        }
        return ansList;
    }

    public int getCntBookByGivenGenre(Genre genre){
        


        return 0;
        
    }
}
