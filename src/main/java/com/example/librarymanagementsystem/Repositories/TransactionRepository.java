package com.example.librarymanagementsystem.Repositories;

import com.example.librarymanagementsystem.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
        @Query(value = "select * from transaction where book_book_id=:bookId and library_card_card_no=:cardId and transaction_status='SUCCESS';",nativeQuery = true)
        List<Transaction>transactionsList(Integer bookId,Integer cardId);

        @Query(value = "select sum(fine)  from transaction where  creation_date between :date  and :date;",nativeQuery = true)
        public Integer totalFine(String date);
}
