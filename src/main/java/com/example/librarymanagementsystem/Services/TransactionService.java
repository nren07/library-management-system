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

import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.el.stream.Optional;
import org.hibernate.id.IntegralDataTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Value("${MaxDaysForFine}")
    private Integer maxDays;

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

    public String returnBook(Integer bookId, Integer cardId) throws Exception{
        //validation check here
        if(!bookRepository.existsById(bookId)) throw new Exception("book id is invalid ");
        if(!cardRepository.existsById(cardId)) throw new Exception("card id is not present");

        Book book=bookRepository.findById(bookId).get();
        LibraryCard libraryCard=cardRepository.findById(cardId).get();

        List<Transaction>transactions=transactionRepository.transactionsList(bookId,cardId);
        Transaction latestTransaction=transactions.get(transactions.size()-1); // last transaction is latest transaction
        Date issueDate=latestTransaction.getCreationDate(); // issuing date
        long issueTime=Math.abs(System.currentTimeMillis()- issueDate.getTime()); // time in milli seconds
        long noOfDays= TimeUnit.DAYS.convert(issueTime,TimeUnit.MILLISECONDS);

        int fine=0;

        if(noOfDays>maxDays) fine=(int)((noOfDays-maxDays)*5);

        Transaction transaction=new Transaction(fine,TransactionStatus.SUCCESS,TransactionType.RETURN);
        book.setAvailable(true);
        transaction.setBook(book); //since by directional mapping is there so no need to save transaction

        libraryCard.setNoOfIssuedBooks(libraryCard.getNoOfIssuedBooks()-1);
        transaction.setLibraryCard(libraryCard);

        Transaction newTransactionWitId=transactionRepository.save(transaction);

        book.getTransactionList().add(newTransactionWitId);
        libraryCard.getTransactionList().add(newTransactionWitId);
        bookRepository.save(book);
        cardRepository.save(libraryCard);

        return "Book has successfully been returned";
    }

    public int getCollectFineInYear(String date){
        return transactionRepository.totalFine(date);
    }

}
