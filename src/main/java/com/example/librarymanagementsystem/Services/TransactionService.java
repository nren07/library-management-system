package com.example.librarymanagementsystem.Services;

import com.example.librarymanagementsystem.Enum.CardStatus;
import com.example.librarymanagementsystem.Enum.TransactionStatus;
import com.example.librarymanagementsystem.Enum.TransactionType;
import com.example.librarymanagementsystem.Models.Book;
import com.example.librarymanagementsystem.Models.LibraryCard;
import com.example.librarymanagementsystem.Models.Transaction;
import com.example.librarymanagementsystem.Repositories.BookRepository;
import com.example.librarymanagementsystem.Repositories.CardRepository;
import com.example.librarymanagementsystem.Repositories.TransactionRepository;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Value("${book.maxLimit}")
    private Integer maxLimit;

    public String issueBookToStudent(int  cardNo,int bookId) throws Exception {
        //validation check

        Transaction transaction=new Transaction(0,TransactionStatus.PENDING,TransactionType.ISSUE);

        //book validation check
        if( !bookRepository.existsById(bookId)){
            throw new Exception("Invalid Book Id");
        }

        Book book=bookRepository.findById(bookId).get();

        //book availability check
        if(!book.isAvailable()) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction=transactionRepository.save(transaction);
            throw new Exception("Book is not Available atPresent");
        }

        //card validation
        if(!cardRepository.existsById(cardNo)){
            throw new Exception("Invalid Card No");
        }

        LibraryCard card=cardRepository.findById(cardNo).get();

        //card status check
        if(card.getCardStatus().equals(CardStatus.BLOCKED)){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction=transactionRepository.save(transaction);
            throw new Exception("Card is blocked");
        }
        //book limit check
        if(card.getNoOfIssuedBooks()>=maxLimit){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction=transactionRepository.save(transaction);
            throw new Exception("Book Limit is over");

        }

        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        book.setAvailable(false); // if book is already issued then make it unavailable 
        card.setNoOfIssuedBooks(card.getNoOfIssuedBooks()+1); //increase the amount of book issued to student 

        //unidirectional mapping saving
        transaction.setLibraryCard(card); 
        transaction.setBook(book);

        //bidirectional mapping 

        card.getTransactionList().add(transaction);
        card.setCardStatus(CardStatus.ACTIVE);
        book.getTransactionList().add(transaction);
        

        //we can save child as well as parent there in this case we need to save both card and book as aprent but 
        // in case of child we will have to save only transaction;
        //so we save only transaction in repository then book and card automatically updated 

        transactionRepository.save(transaction);

        return "Book is successfully issued to student.";
    }


}
